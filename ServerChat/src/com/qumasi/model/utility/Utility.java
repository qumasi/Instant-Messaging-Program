package com.qumasi.model.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Utility {
      //https://stackoverflow.com/questions/3736058/java-object-to-byte-and-byte-to-object-converter-for-tokyo-cabinet/3736091
    public static byte[] convertObjectToBytes(Object obj)  {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(obj);
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object convertBytesToObject(byte[] data)  {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        try {
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }
}
