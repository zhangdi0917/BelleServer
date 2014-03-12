package com.jesson.sexybelle.servlet;

import com.google.gson.Gson;
import com.jesson.sexybelle.dao.DAOFactory;
import com.jesson.sexybelle.dao.IBelleDAO;
import com.jesson.sexybelle.dao.IVerifyDAO;
import com.jesson.sexybelle.model.Belle;
import com.jesson.sexybelle.servlet.response.RandomGetBelleListResponse;
import com.jesson.sexybelle.servlet.response.ServerErrorResponse;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdi on 14-3-7.
 */
public class RandomGetBelles extends HttpServlet implements Servlet {

    private Logger logger = Logger.getLogger(RandomGetBelles.class.getSimpleName());

    @Override
    public void init() throws ServletException {
        String path = getServletContext().getRealPath("/");
        PropertyConfigurator.configure(path + "resource/log4j.properties");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sType = req.getParameter("type");
        String sCount = req.getParameter("count");
        String appid = req.getParameter("appid");

        logger.debug("random get belles: type=" + sType + ",count=" + sCount + ", appid=" + appid);

        Gson gson = new Gson();
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();

        try {
            IVerifyDAO verifyDAO = DAOFactory.getIVerifyDAOInstance();
            if (!verifyDAO.verifyAppid(appid)) {
                ServerErrorResponse err = new ServerErrorResponse(ServerErrorResponse.VERIFY_ERROR, "appid验证失败");
                logger.error(err.toString());
                writer.print(gson.toJson(err));
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServerErrorResponse err = new ServerErrorResponse(ServerErrorResponse.DATABASE_ERROR, "数据库错误");
            logger.error(err.toString() + e.getMessage());
            writer.print(gson.toJson(err));
            return;
        }

        int type;
        int count;
        try {
            type = Integer.parseInt(sType);
            count = Integer.parseInt(sCount);
        } catch (NumberFormatException e) {
            ServerErrorResponse err = new ServerErrorResponse(ServerErrorResponse.PARAM_ERROR, "parameter error");
            logger.error(err.toString() + e.getMessage());
            writer.print(gson.toJson(err));
            return;
        }

        try {
            IBelleDAO belleDAO = DAOFactory.getIBelleDAOInstance();
            List<Belle> belles = belleDAO.randomFindByType(type, count);
            if (belles == null) {
                belles = new ArrayList<Belle>();
            }
            RandomGetBelleListResponse response = new RandomGetBelleListResponse();
            response.belles = belles;

            writer.print(gson.toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
            ServerErrorResponse err = new ServerErrorResponse(ServerErrorResponse.DATABASE_ERROR, "数据库错误");
            logger.error(err.toString() + e.getMessage());
            writer.print(gson.toJson(err));
        }
    }
}
