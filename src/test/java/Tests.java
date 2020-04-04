import de.perschon.resultflow.Result;
import no.netb.Indexer;
import no.netb.models.FsNode;
import no.netb.models.Host;
import no.netb.models.TablesInit;
import no.netb.repository.Repository;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class Tests {

    @BeforeClass
    public static void testCreateTables() {
        try {
            TablesInit.createTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSelectN() {
        Result<List<FsNode>, Exception> selectResult = Repository.selectN(FsNode.class, "WHERE f.isFile = ?", 0);
        if (selectResult.isErr()) {
            throw new RuntimeException("select failed:", selectResult.getError().get());
        }
        List<FsNode> fsNodes = selectResult.unwrap();
    }

    @Test
    public void testIndex() {
        Result<List<Host>, Exception> selectResult = Repository.selectN(Host.class, "WHERE h.id = ?", 1);
        Result<Object, String> indexResult = Indexer.index(selectResult.unwrap().get(0), new File("/home"));
    }
}
