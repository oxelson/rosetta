package edu.ucar.unidata.rosetta.repository;

import edu.ucar.unidata.rosetta.domain.RosettaProperties;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Implementation of a properties DAO.
 *
 * @author oxelson@ucar.edu
 */
public class JdbcPropertiesDao extends JdbcDaoSupport implements PropertiesDao {

    protected static Logger logger = Logger.getLogger(JdbcPropertiesDao.class);

    private SimpleJdbcInsert insertActor;

    /**
     * Looks up and retrieves the persisted uploads directory.
     *
     * @return The persisted uploads directory.
     * @throws DataRetrievalFailureException  If unable to lookup uploads directory.
     */
    @Override
    public String lookupUploadDirectory() throws DataRetrievalFailureException {
        String sql = "SELECT * FROM properties WHERE propertyKey = 'rosetta.uploadDir'";
        List<RosettaProperties> properties = getJdbcTemplate().query(sql, new JdbcPropertiesDao.DataMapper());
        if (properties.isEmpty()) {
            String message = "Unable to find persisted Rosetta properties";
            logger.error(message);
            throw new DataRetrievalFailureException(message);
        }
        return properties.get(0).getPropertyValue();
    }

    /**
     * Looks up and retrieves the persisted downloads directory.
     *
     * @return The persisted downloads directory.
     */
    public String lookupDownloadDirectory() {
        String sql = "SELECT * FROM properties WHERE propertyKey = 'rosetta.downloadDir'";
        List<RosettaProperties> properties = getJdbcTemplate().query(sql, new JdbcPropertiesDao.DataMapper());
        if (properties.isEmpty()) {
            String message = "Unable to find persisted Rosetta properties";
            logger.error(message);
            throw new DataRetrievalFailureException(message);
        }
        return properties.get(0).getPropertyValue();
    }

    /**
     * This DataMapper only used by JdbcPropertiesDao.
     */
    private static class DataMapper implements RowMapper<RosettaProperties> {
        /**
         * Maps each row of rosettaProperties in the ResultSet to the RosettaProperties object.
         *
         * @param rs  The ResultSet to be mapped.
         * @param rowNum  The number of the current row.
         * @return  The populated RosettaProperties object.
         * @throws SQLException  If an SQLException is encountered getting column values.
         */
        public RosettaProperties mapRow(ResultSet rs, int rowNum) throws SQLException {
            RosettaProperties rosettaProperties = new RosettaProperties();
            rosettaProperties.setId(rs.getInt("id"));
            rosettaProperties.setPropertyKey(rs.getString("propertyKey"));
            rosettaProperties.setPropertyValue(rs.getString("propertyValue"));
            return rosettaProperties;
        }
    }
}
