package org.embulk.decoder.remove_nonstandard_utf8_bytes;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.embulk.config.ConfigInject;
import org.embulk.config.ConfigSource;
import org.embulk.config.Task;
import org.embulk.config.TaskSource;
import org.embulk.spi.BufferAllocator;
import org.embulk.spi.DecoderPlugin;
import org.embulk.spi.FileInput;
import org.embulk.spi.util.FileInputInputStream;
import org.embulk.spi.util.InputStreamFileInput;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

public class RemoveNonstandardUtf8BytesDecoderPlugin
        implements DecoderPlugin
{
    interface PluginTask
            extends Task
    {
        @ConfigInject
        BufferAllocator getBufferAllocator();
    }

    @Override
    public void transaction(ConfigSource config, DecoderPlugin.Control control)
    {
        PluginTask task = config.loadConfig(PluginTask.class);

        control.run(task.dump());
    }

    @Override
    public FileInput open(TaskSource taskSource, FileInput fileInput)
    {
        final CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();
        charsetDecoder.onMalformedInput(CodingErrorAction.IGNORE);
        charsetDecoder.onUnmappableCharacter(CodingErrorAction.IGNORE);

        final FileInputInputStream files = new FileInputInputStream(fileInput);
        PluginTask task = taskSource.loadTask(PluginTask.class);

        return new InputStreamFileInput(
                task.getBufferAllocator(),
                new InputStreamFileInput.Provider()
                {
                    public InputStream openNext()
                            throws IOException
                    {
                        if (!files.nextFile()) {
                            return null;
                        }
                        Reader reader = new BufferedReader(new InputStreamReader(files, charsetDecoder));
                        return new ByteArrayInputStream(CharStreams.toString(reader).getBytes(Charsets.UTF_8));
                    }

                    public void close()
                            throws IOException
                    {
                        files.close();
                    }
                });
    }
}
