package com.fm.fmlib.tour;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.GsonConverter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by qieyou on 2015/7/30.
 */
public class StringConvert extends GsonConverter {
    private String charset;
    public StringConvert(Gson gson) {
        super(gson);
        charset = "UTF-8";
    }

    @Override public String fromBody(TypedInput body, Type type) throws ConversionException {
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType(), charset);
        }
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(body.in(), charset);

            BufferedReader reader = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();



            String line = null;

            try {

                while ((line = reader.readLine()) != null) {

                    sb.append(line);

                }

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                try {

                    isr.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }
            return sb.toString();
        } catch (IOException e) {
            throw new ConversionException(e);
        } catch (JsonParseException e) {
            throw new ConversionException(e);
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override public TypedOutput toBody(Object object) {
            return null;
    }
}
