package ebi.spot.vfbtriplestore.setup;

import org.eclipse.rdf4j.rio.RDFFormat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SetupTripleStore {
    public static void main(String[] args) {

        File inputdir = new File(args[0]);
        File storedir = new File(args[1]);
        VFBTripleStore store = new VFBTripleStore();

        if(!inputdir.exists()) {
            throw new IllegalArgumentException("Input dir "+inputdir+" does not exist");
        }
        if(!storedir.exists()) {
            throw new IllegalArgumentException("Store dir "+storedir+" does not exist");
        }

        try {
            Files.newDirectoryStream(Paths.get(inputdir.getAbsolutePath()),
                    path -> path.toString().endsWith(".gz"))
                    .forEach(f-> {
                        try {
                            addToRepo(f, store);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    private static void addToRepo(Path f, VFBTripleStore store) throws IOException {
        store.loadZippedFile(Files.newInputStream(f),
                RDFFormat.TURTLE);
    }
}
