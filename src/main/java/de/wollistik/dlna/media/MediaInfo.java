/**
 * 
 */
package de.wollistik.dlna.media;

import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

/**
 * @author Wolfgang Wedelich-John<wolfgang.wedelich@wollistik.de>
 *
 */
public class MediaInfo {
    private String filename;

    private long duration;
    private int bitRate;

    private String type;

    public MediaInfo(String filename) {
        this.filename = filename;
    }

    private void readInformation() {
        IContainer container = IContainer.make();

        if (container.open(filename,
                IContainer.Type.READ,
                null) < 0) {
            // TODO throw error
        }

        duration = container.getDuration() == Global.NO_PTS ? -1l : container.getDuration();
        bitRate = container.getBitRate();

        for (int i = 0; i < container.getNumStreams(); i++) {
            IStream stream = container.getStream(i);

            // Get the pre-configured decoder that can decode this stream;
            IStreamCoder coder = stream.getStreamCoder();

            if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
                System.out.printf("sample rate: %d; ",
                        coder.getSampleRate());
                System.out.printf("channels: %d; ",
                        coder.getChannels());
                System.out.printf("format: %s",
                        coder.getSampleFormat());
            } else if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
                System.out.printf("width: %d; ",
                        coder.getWidth());
                System.out.printf("height: %d; ",
                        coder.getHeight());
                System.out.printf("format: %s; ",
                        coder.getPixelType());
                System.out.printf("frame-rate: %5.2f; ",
                        coder.getFrameRate().getDouble());

            }
            System.out.printf("\n");
        }
    }

    public static void main(String[] args) {
        MediaInfo info = new MediaInfo("/home/wolle/Videos/Findet Nemo - 720p.mkv");

        info.readInformation();
    }
}
