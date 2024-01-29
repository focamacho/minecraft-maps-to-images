import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import se.llbit.nbt.CompoundTag;
import se.llbit.nbt.NamedTag;
import se.llbit.nbt.Tag;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java -jar map-to-img.jar [directory]");
            System.out.println("Application will search up to 3 levels deep in given directory, so it can be a world or " +
                    "just the .minecraft/saves folder.");
            System.exit(1);
        }
        String folder = args[0];

        List<Path> mapFiles = Files.find(Paths.get(folder), 3, (path, attr) ->
                path.getFileName().toString().matches("map_[0-9]+\\.dat")
        ).collect(Collectors.toList());

        Path out = Paths.get(folder, "out");
        Files.createDirectories(out);
        System.out.println("Writing files to " + out);

        int num = 0;

        for (Path p : mapFiles) {
            try {
                BufferedImage img = new BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR);
                CompoundTag t = read(p.toFile()).unpack().get("data").asCompound();
                byte[] data = t.get("colors").byteArray();

                for (int i = 0; i < 16384; ++i) {
                    int color;
                    int j = data[i] & 255;

                    if (j / 4 == 0) {
                        color = (i + i / 128 & 1) * 8 + 16 << 24;
                    } else {
                        color = BasicColor.colors.get(j / 4).shaded(j & 3);
                    }

                    img.setRGB(i % 128, i / 128, color);
                }

                // write image to temp location
                Path outFile = Paths.get(out.toString(), p.getFileName().toString().replace(".dat", ".png"));
                Path tempFile = Paths.get(out.toString(), "temp");
                ImageIO.write(img, "png", tempFile.toFile());

                // overwrite file only if its identical, otherwise rename the current file
                if (outFile.toFile().exists()) {
                    if (!FileUtils.contentEquals(outFile.toFile(), tempFile.toFile())) {
                        long time = System.nanoTime();
                        outFile = Paths.get(outFile.toString().replace(".png", "-" + time + ".png"));
                    }
                }

                Files.move(tempFile, outFile, StandardCopyOption.REPLACE_EXISTING);
                tempFile.toFile().renameTo(outFile.toFile());

                num++;

                System.out.print(MessageFormat.format("Converting maps... Progress: {0}% ({1}/{2})\r",
                        (int) (100.0 / mapFiles.size() * num),
                        num,
                        mapFiles.size())
                );
                System.out.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println();
        System.out.println("Done! Converted " + num + " maps.");
    }

    public static Tag read(File f) throws IOException {
        InputStream input = new FileInputStream(f);
        byte[] fileContent = IOUtils.toByteArray(input);
        return NamedTag.read(
                new DataInputStream(new ByteArrayInputStream(gzipDecompress(fileContent)))
        );
    }

    // Source: https://stackoverflow.com/a/44922240
    public static byte[] gzipDecompress(byte[] compressedData) {
        byte[] result = new byte[]{};
        try (ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
             ByteArrayOutputStream bos = new ByteArrayOutputStream();
             GZIPInputStream gzipIS = new GZIPInputStream(bis)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipIS.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

