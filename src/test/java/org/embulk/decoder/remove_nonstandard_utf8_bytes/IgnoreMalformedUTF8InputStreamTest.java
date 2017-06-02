package org.embulk.decoder.remove_nonstandard_utf8_bytes;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class IgnoreMalformedUTF8InputStreamTest
{

    @Test
    public void spec()
    throws Exception {
        InputStream in = IgnoreMalformedUTF8InputStream.get(createMelformedStream());
        int n = in.available();
        byte[] bytes = new byte[n];
        in.read(bytes, 0, n);
        String s = new String(bytes, StandardCharsets.UTF_8);
        assertThat(s.getBytes() , is("ab".getBytes()));
    }

    private ByteArrayInputStream createMelformedStream() throws IOException
    {
        // out of utf-8 range's byte.
        // 0xF0 is backslash.
        byte[] brokenBytes = { Integer.valueOf(0xF0).byteValue() };
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // contain illegal char record
        outputStream.write("a".getBytes("UTF-8"));
        outputStream.write(brokenBytes); // append dust bytes.
        outputStream.write("b".getBytes("UTF-8"));

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

}