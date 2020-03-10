package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientDAO {

    private static final Logger logger = LogManager.getLogger("ClientDAO");

    public DataBaseConfig dataBaseConfig = new DataBaseConfig();


    public boolean getClient(String vehicleRegNumber) {
        Connection con = null;
        int test=0;
        boolean rec = false;
        try {
            con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBConstants.GET_CLIENT);
            ps.setString(1, vehicleRegNumber);
            ResultSet rs = ps.executeQuery();
            rs.next();
            test = rs.getInt(1);

            if(test>1){rec = true;}
            dataBaseConfig.closePreparedStatement(ps);
        }catch (Exception ex){
            logger.error("Error fetching next available slot",ex);
        }finally {
            dataBaseConfig.closeConnection(con);
        }
        return rec;
    }

}
