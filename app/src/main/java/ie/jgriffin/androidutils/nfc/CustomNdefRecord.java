package ie.jgriffin.androidutils.nfc;

import java.nio.charset.Charset;
import java.util.ArrayList;

import ie.jgriffin.androidutils.ByteUtils;

/**
 * Created by JGriffin on 26/06/2014.
 */
public class CustomNdefRecord {
    //We don't really need to worry about chunked records as the max size of a record is defined as 2^32-1
    //That is bigger than the entire memory on the chip
    //Likewise we don't need to worry about an id or id length as we don't use them

    //static fields
    public static final byte TNF_WELL_KNOWN = 0x01;
    public static final byte TNF_MIME_MEDIA = 0x02;
    public static final byte TNF_EXTERNAL_TYPE = 0x04;

    public static final byte[] RTD_URI = {0x55};   // "U"


    //instance fields
    private final byte mTnf;
    private final byte[] mType;
    private final byte[] mPayload;


    /*
    On android the default Charset is UTF-8
    so this maps to
    a       n       d       r       o       i       d       .       c       o       m       :       p       k       g
    0x61    0x6E    0x64    0x72    0x6F    0X69    0X64    0X2E    0X63    0X6F    0X6D    0X3A    0X70    0X6B    0X67
    97      110     100     114     111     105     100     46      99      111     109     58      112     107     103
     */
    public static final byte[] RTD_ANDROID_APP = "android.com:pkg".getBytes();

    /**
     * NFC Forum "URI Record Type Definition"
     * <p/>
     * This is a mapping of "URI Identifier Codes" to URI string prefixes,
     * per section 3.2.2 of the NFC Forum URI Record Type Definition document.
     */
    private static final String[] URI_PREFIX_MAP = new String[]{
            "", // 0x00
            "http://www.", // 0x01
            "https://www.", // 0x02
            "http://", // 0x03
            "https://", // 0x04
            "tel:", // 0x05
            "mailto:", // 0x06
            "ftp://anonymous:anonymous@", // 0x07
            "ftp://ftp.", // 0x08
            "ftps://", // 0x09
            "sftp://", // 0x0A
            "smb://", // 0x0B
            "nfs://", // 0x0C
            "ftp://", // 0x0D
            "dav://", // 0x0E
            "news:", // 0x0F
            "telnet://", // 0x10
            "imap:", // 0x11
            "rtsp://", // 0x12
            "urn:", // 0x13
            "pop:", // 0x14
            "sip:", // 0x15
            "sips:", // 0x16
            "tftp:", // 0x17
            "btspp://", // 0x18
            "btl2cap://", // 0x19
            "btgoep://", // 0x1A
            "tcpobex://", // 0x1B
            "irdaobex://", // 0x1C
            "file://", // 0x1D
            "urn:epc:id:", // 0x1E
            "urn:epc:tag:", // 0x1F
            "urn:epc:pat:", // 0x20
            "urn:epc:raw:", // 0x21
            "urn:epc:", // 0x22
    };


    public static CustomNdefRecord createURIRecord(String uriString) {
        byte prefix = 0x00;
        for (int i = 1; i < URI_PREFIX_MAP.length; i++) {
            if (uriString.startsWith(URI_PREFIX_MAP[i])) {
                prefix = (byte) i;
                uriString = uriString.substring(URI_PREFIX_MAP[i].length());
                break;
            }
        }
        byte[] uriBytes = uriString.getBytes(Charset.forName("UTF-8"));
        byte[] recordBytes = new byte[uriBytes.length + 1];
        recordBytes[0] = prefix;
        System.arraycopy(uriBytes, 0, recordBytes, 1, uriBytes.length);
        return new CustomNdefRecord(TNF_WELL_KNOWN, RTD_URI, recordBytes);
    }



    /*
    For everything not outlined in the NFC Forum NDEF standard the Android SDK seems to favour using ASCII
    I'm not sure why, but we have to comply for the AAR as Android probably expects ASCII when decoding.
     */
    public static CustomNdefRecord createApplicationRecord(String packageName) {
        return new CustomNdefRecord(TNF_EXTERNAL_TYPE, RTD_ANDROID_APP, packageName.getBytes(Charset.forName("US-ASCII")));
    }


    /**
     * Construct an NDEF Record.
     *
     * @param tnf     a 3-bit TNF constant
     * @param type    byte array, containing zero to 255 bytes, must not be null
     * @param payload byte array, containing zero to (2 ** 32 - 1) bytes,
     *                must not be null
     */
    public CustomNdefRecord(byte tnf, byte[] type, byte[] payload) {
        if ((type == null) || (payload == null)) {
            throw new IllegalArgumentException("Illegal null argument");
        }

        if (tnf < 0 || tnf > 0x07) {
            throw new IllegalArgumentException("TNF out of range " + tnf);
        }

        mTnf = tnf;
        mType = type.clone();
        mPayload = payload.clone();
    }




    /**
     * Returns this entire NDEF Record as a byte array.
     */
    public byte[] toByteArray() {
        /*
        Record head byte layout:
        bit 7 -  Message Begin flag Only set in first record of message.
        bit 6 -  Message End flag Only set in last record of message.
                 We will leave the MB and ME flags set to 0 and only alter them at the NdefMessage level when
                 serializing all records.

        bit 5 -  Chunk flag. We are not using chunked records
        bit 4 -  Short Record flag. Indicates that the payload length is less than 0xFF and therefore the Payload Length
                 field is encoded on one byte instead of four.
        bit 3 -  ID Length flag. Indicates that the ID Length field is present in the header. We do not use these IDs.
        bits 2-0 TNF. Type Name Format. This is defined by the TNF constants created at the top of this class. We are
                 only interested in the WELL_KNOWN, MIME and EXTERNAL types. There are others but I have omitted them.
         */

        ArrayList<Byte> result = new ArrayList<Byte>();
        //create the record head byte and set bits 2 - 0 to the passed TNF value.
        byte recordHeadByte = mTnf;


        byte typeLength = (byte) mType.length;

        //this can be one or four bytes depending on the size of the payload
        byte[] payloadLength;

        //set short record flag if required and construct payload length array
        if (mPayload.length <= 0xFF) {
            recordHeadByte = ByteUtils.setBit(recordHeadByte, 4, true);
            payloadLength = new byte[]{(byte) mPayload.length};
        } else {
            payloadLength = new byte[]{(byte) (mPayload.length >>> 24), (byte) (mPayload.length >>> 16),
                    (byte) (mPayload.length >>> 8), (byte) (mPayload.length)};
        }


        //add record head byte to start of arrayList
        result.add(recordHeadByte);

        //add type length to the arrayList
        result.add(typeLength);

        //add the payload length to the arrayList
        for (byte aPayloadLength : payloadLength) {
            result.add(aPayloadLength);
        }

        //add the actual type
        for (byte aMType : mType) {
            result.add(aMType);
        }

        //add the actual payload
        for (byte aMPayload : mPayload) {
            result.add(aMPayload);
        }

        //convert arrayList to primitive array
        byte[] resultArray = new byte[result.size()];
        for(int i=0; i<resultArray.length; i++){
            resultArray[i] = result.get(i);
        }


        return resultArray;
    }
}
