package org.embulk.decoder.remove_nonstandard_utf8_bytes;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

public class IgnoreMalformedUTF8InputStream {

    static InputStream get(InputStream is)
            throws IOException
    {
        final CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();
        charsetDecoder.onMalformedInput(CodingErrorAction.IGNORE);
        charsetDecoder.onUnmappableCharacter(CodingErrorAction.IGNORE);

        Reader reader = new BufferedReader(new InputStreamReader(is, charsetDecoder));
        return new ByteArrayInputStream(CharStreams.toString(reader).getBytes(Charsets.UTF_8));
    }
}
