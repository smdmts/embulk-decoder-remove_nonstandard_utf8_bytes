package org.embulk.decoder.remove_nonstandard_utf8_bytes;

import java.io.InputStream;
import java.io.IOException;

import com.google.common.base.Optional;

import org.embulk.config.Config;
import org.embulk.config.ConfigDefault;
import org.embulk.config.ConfigInject;
import org.embulk.config.ConfigSource;
import org.embulk.config.Task;
import org.embulk.config.TaskSource;
import org.embulk.spi.BufferAllocator;
import org.embulk.spi.DecoderPlugin;
import org.embulk.spi.FileInput;
import org.embulk.spi.util.FileInputInputStream;
import org.embulk.spi.util.InputStreamFileInput;

public class RemoveNonstandardUtf8BytesDecoderPlugin
        implements DecoderPlugin
{
    public interface PluginTask
            extends Task
    {
        // configuration option 1 (required integer)
        @Config("option1")
        public int getOption1();

        // configuration option 2 (optional string, null is not allowed)
        @Config("option2")
        @ConfigDefault("\"myvalue\"")
        public String getOption2();

        // configuration option 3 (optional string, null is allowed)
        @Config("option3")
        @ConfigDefault("null")
        public Optional<String> getOption3();

        @ConfigInject
        public BufferAllocator getBufferAllocator();
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
        final PluginTask task = taskSource.loadTask(PluginTask.class);

        // Write your code here :)
        throw new UnsupportedOperationException("RemoveNonstandardUtf8BytesDecoderPlugin.open method is not implemented yet");

        // If expect InputStream, you can use this code:

        //final FileInputInputStream files = new FileInputInputStream(fileInput);
        //
        //return new InputStreamFileInput(
        //        task.getBufferAllocator(),
        //        new InputStreamFileInput.Provider() {
        //            public InputStream openNext() throws IOException
        //            {
        //                if (!files.nextFile()) {
        //                    return null;
        //                }
        //                return newDecoderInputStream(task, files);
        //            }
        //
        //            public void close() throws IOException
        //            {
        //                files.close();
        //            }
        //        });
    }

    //private static InputStream newDecoderInputStream(PluginTask task, InputStream file) throws IOException
    //{
    //    return new MyInputStream(file);
    //}
}
