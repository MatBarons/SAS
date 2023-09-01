package catering.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import catering.businesslogic.UseCaseLogicException;

public interface BatchUpdateHandler {
    public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException;
    public void handleGeneratedIds(ResultSet rs, int count) throws SQLException;
}
