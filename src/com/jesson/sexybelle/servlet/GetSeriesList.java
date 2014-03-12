package com.jesson.sexybelle.servlet;

import com.google.gson.Gson;
import com.jesson.sexybelle.dao.DAOFactory;
import com.jesson.sexybelle.dao.ISeriesDAO;
import com.jesson.sexybelle.dao.IVerifyDAO;
import com.jesson.sexybelle.model.Series;
import com.jesson.sexybelle.servlet.response.GetSeriesListResponse;
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
 * Created by zhangdi on 14-3-11.
 */
public class GetSeriesList extends HttpServlet implements Servlet {

    private Logger logger = Logger.getLogger(GetSeriesList.class.getSimpleName());

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
        String sMode = req.getParameter("mode");
        String appid = req.getParameter("appid");

        logger.debug("getSeriesList: appid=" + appid + ", mode=" + sMode);

        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

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
            logger.error(err.toString(), e);
            writer.print(gson.toJson(err));
            return;
        }

        int mode;
        try {
            mode = Integer.parseInt(sMode);
        } catch (NumberFormatException e) {
            ServerErrorResponse err = new ServerErrorResponse(ServerErrorResponse.PARAM_ERROR, "parameter error");
            logger.error(err.toString(), e);
            writer.print(gson.toJson(err));
            return;
        }

        try {
            ISeriesDAO dao = DAOFactory.getISeriesDAOInstance();
            List<Series> seriesList = dao.getAll(mode);
            if (seriesList == null) {
                seriesList = new ArrayList<Series>();
            }
            GetSeriesListResponse response = new GetSeriesListResponse();
            response.seriesList = seriesList;

            writer.print(gson.toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
            ServerErrorResponse err = new ServerErrorResponse(ServerErrorResponse.DATABASE_ERROR, "数据库错误");
            logger.error(err.toString(), e);
            writer.print(gson.toJson(err));
        }
    }
}
