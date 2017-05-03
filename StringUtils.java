package com.tryndamere.lzm.kuaikanmanhua.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class StringUtils {


    public static String inputStreamToString(InputStream inputStream) {

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] buf = new byte[1024];

            int length = 0;

            while ((length = inputStream.read(buf)) != -1) {

                byteArrayOutputStream.write(buf, 0, length);

            }

            return new String(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String inputStraemToStringBuffer(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String line = null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return stringBuilder.toString();
        }


    }

}
