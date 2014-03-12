package com.jesson.sexybelle.servlet;

import com.google.gson.Gson;
import com.jesson.sexybelle.dao.DAOFactory;
import com.jesson.sexybelle.dao.IBelleDAO;
import com.jesson.sexybelle.dao.IVerifyDAO;
import com.jesson.sexybelle.model.Belle;
import com.jesson.sexybelle.servlet.response.GetBelleListResponse;
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
 * Created by zhangdi on 14-3-6.
 */
public class GetBelles extends HttpServlet implements Servlet {

    private Logger logger = Logger.getLogger(GetBelles.class.getSimpleName());

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
        String sId = req.getParameter("id");
        String sCount = req.getParameter("count");
        String appid = req.getParameter("appid");

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

        int type;
        long id;
        int count;
        try {
            type = Integer.parseInt(sType);
            id = Long.parseLong(sId);
            count = Integer.parseInt(sCount);
        } catch (NumberFormatException e) {
            ServerErrorResponse err = new ServerErrorResponse(ServerErrorResponse.PARAM_ERROR, "parameter error");
            logger.error(err.toString(), e);
            writer.print(gson.toJson(err));
            return;
        }

        logger.debug("type=" + type + ",id=" + id + ",count=" + count);

        try {
            IBelleDAO belleDAO = DAOFactory.getIBelleDAOInstance();
            List<Belle> belles = belleDAO.findAllByType(type, id, count + 1);
            if (belles == null) {
                belles = new ArrayList<Belle>();
            }
            GetBelleListResponse response = new GetBelleListResponse();
            if (belles.size() > count) {
                response.hasMore = true;
                belles.remove(belles.size() - 1);
            } else {
                response.hasMore = false;
            }
            response.belles = belles;

            writer.print(gson.toJson(response));
        } catch (Exception e) {
            e.printStackTrace();
            ServerErrorResponse err = new ServerErrorResponse(ServerErrorResponse.DATABASE_ERROR, "数据库错误");
            logger.error(err.toString(), e);
            writer.print(gson.toJson(err));
        }
    }
}
