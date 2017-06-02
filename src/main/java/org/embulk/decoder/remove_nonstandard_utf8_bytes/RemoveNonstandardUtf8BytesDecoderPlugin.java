package org.embulk.decoder.remove_nonstandard_utf8_bytes;

import org.embulk.config.ConfigInject;
import org.embulk.config.ConfigSource;
import org.embulk.config.Task;
import org.embulk.config.TaskSource;
import org.embulk.spi.BufferAllocator;
import org.embulk.spi.DecoderPlugin;
import org.embulk.spi.FileInput;
import org.embulk.spi.util.FileInputInputStream;
import org.embulk.spi.util.InputStreamFileInput;

import java.io.IOException;
import java.io.InputStream;

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
                        return IgnoreMalformedUTF8InputStream.get(files);
                    }

                    public void close()
                            throws IOException
                    {
                        files.close();
                    }
                });
    }



}
