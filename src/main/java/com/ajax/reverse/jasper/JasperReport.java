package com.ajax.reverse.jasper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import com.ajax.reverse.domain.Channel;
import com.ajax.reverse.service.JasperDatasourceService;

@Component
public class JasperReport extends JasperReportsPdfView {

    @Autowired
    private JasperDatasourceService jasperDatasourceService;

    @Override
    protected JasperPrint fillReport(Map<String, Object> model) throws Exception {
        String channelName = null;
        Object channelObject = model.get("channel");
        if (channelObject != null) {
            if (channelObject instanceof Channel) {
                channelName = ((Channel) channelObject).getName();
            } else {
                channelName = (String) channelObject;
            }
            JRDataSource jRDataSource = jasperDatasourceService.getDataSourceForChannel(channelName);
            model.put("datasource", jRDataSource);
        }
        return super.fillReport(model);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.renderMergedOutputModel(model, request, response);
        if (!model.containsKey("channel")) {
            String url = request.getRequestURL().toString();
            String channel = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
            model.put("channel", channel);
        }
    }

}
