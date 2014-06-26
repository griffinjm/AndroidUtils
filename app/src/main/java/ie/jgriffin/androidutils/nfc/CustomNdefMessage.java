package ie.jgriffin.androidutils.nfc;

import ie.jgriffin.androidutils.ByteUtils;

/**
 * Created by JGriffin on 26/06/2014.
 */
public class CustomNdefMessage {

    private final CustomNdefRecord[] mRecords;

    /**
     * Create an NDEF message from NDEF records.
     */
    public CustomNdefMessage(CustomNdefRecord[] records) {
        mRecords = new CustomNdefRecord[records.length];
        System.arraycopy(records, 0, mRecords, 0, records.length);
    }

    /**
     * Returns a byte array representation of this entire NDEF message.
     */
    public byte[] toByteArray() {
        if ((mRecords == null) || (mRecords.length == 0))
            return new byte[0];

        byte[] msg = {};

        for (int i = 0; i < mRecords.length; i++) {
            byte[] record = mRecords[i].toByteArray();
            byte[] tmp = new byte[msg.length + record.length];

            //set Message Begin flag if first record
            if (i == 0) {
                record[0] = ByteUtils.setBit(record[0], 7, true);
            }

            //set Message End flag if last record
            if (i == (mRecords.length - 1)) {
                record[0] = ByteUtils.setBit(record[0], 6, true);
            }

            System.arraycopy(msg, 0, tmp, 0, msg.length);
            System.arraycopy(record, 0, tmp, msg.length, record.length);

            msg = tmp;
        }

        return msg;
    }

}
