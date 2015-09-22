package com.fm.fmlib.tour;

import android.util.Log;

import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.tour.entity.BaseEntity;
import com.fm.fmlib.tour.entity.TempEntity;
import com.fm.fmlib.utils.TypeUtil;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class YangtaoConvert implements Converter {
    private final Gson gson;
    private String charset;

    /**
     * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public YangtaoConvert(Gson gson) {
        this(gson, "UTF-8");
    }

    /**
     * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use the specified charset.
     */
    public YangtaoConvert(Gson gson, String charset) {
        this.gson = gson;
        this.charset = charset;
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        String charset = this.charset;
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType(), charset);
        }
        InputStreamReader isr = null;
        try {

            isr = new InputStreamReader(body.in(), charset);

            String json = getStream2String(isr);
            Log.v(NetworkCallRunnable.TAG,"result fromBody  ::::::::"+json );
            TempEntity temp = gson.fromJson(json, TempEntity.class);
            if (1 != temp.code) {
                Object result =TypeUtil.newInstance(type);
                ((BaseEntity) result).errorInfo = (String) temp.msg;
                ((BaseEntity) result).code = temp.code;
                return result;
            }
            Object re = gson.fromJson(json, type);
            return re;
        } catch (IOException e) {
            throw new ConversionException(e);
        } catch (JsonParseException e) {
            throw new ConversionException(e);
        } catch (ClassNotFoundException e) {
            throw new ConversionException(e);
        } catch (InstantiationException e) {
            throw new ConversionException(e);
        } catch (IllegalAccessException e) {
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

    @Override
    public TypedOutput toBody(Object object) {
        try {
            Log.v(NetworkCallRunnable.TAG,"result toBody  ::::::::"+gson.toJson(object) );
            return new JsonTypedOutput(gson.toJson(object).getBytes(charset), charset);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }


    private static class JsonTypedOutput implements TypedOutput {
        private final byte[] jsonBytes;
        private final String mimeType;

        JsonTypedOutput(byte[] jsonBytes, String encode) {
            this.jsonBytes = jsonBytes;
            this.mimeType = "application/json; charset=" + encode;
        }

        @Override
        public String fileName() {
            return null;
        }

        @Override
        public String mimeType() {
            return mimeType;
        }

        @Override
        public long length() {
            return jsonBytes.length;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            out.write(jsonBytes);
        }
    }

    private static class TextTypedOutput implements TypedOutput {
        private final byte[] textBytes;
        private final String mimeType;

        TextTypedOutput(byte[] textBytes, String encode) {
            this.textBytes = textBytes;
            this.mimeType = " application/x-www-form-urlencoded; charset=" + encode;
        }

        @Override
        public String fileName() {
            return null;
        }

        @Override
        public String mimeType() {
            return mimeType;
        }

        @Override
        public long length() {
            return textBytes.length;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            out.write(textBytes);
        }
    }

    private String getStream2String(InputStreamReader isr){
        StringBuilder sb = new StringBuilder();

        BufferedReader reader = new BufferedReader(isr);
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
    }

    private String toStrParams(Object obj) {
        StringBuffer sb = new StringBuffer();
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0, len = fields.length; i < len; i++) {
                // 对于每个属性，获取属性名
                String varName = fields[i].getName();
                // 获取原来的访问控制权限Øß
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(this);
                sb.append("&");
                sb.append(varName);
                sb.append("=");
                sb.append(o);
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        if (0 < sb.length()) {
            return sb.substring(1, sb.length());
        }
        return sb.toString();
    }


}
