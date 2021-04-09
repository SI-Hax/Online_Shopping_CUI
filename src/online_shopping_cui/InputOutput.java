package online_shopping_cui;

import java.io.IOException;
import java.util.List;

/**
 * This Interface class maintains a set of tools.
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.0
 * @since 04/04/2021
 **/
public interface InputOutput {
    public void writeCSV();
    public List<List<String>> readCSV() throws IOException;
}
