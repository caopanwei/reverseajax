package com.ajax.reverse.jasper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import com.ajax.reverse.service.ChannelService;
import com.ajax.reverse.service.MessageService;

@Component
public class JasperReport extends JasperReportsPdfView {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private MessageService messageService;
    private JRDataSource jRDataSource;

    @Override
    protected JasperPrint fillReport(Map<String, Object> model) throws Exception {
        model.put("datasource", jRDataSource);
        return super.fillReport(model);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.renderMergedOutputModel(model, request, response);
        String url = request.getRequestURL().toString();
        String channel = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        jRDataSource = new JRBeanCollectionDataSource(messageService.findMessagesByChannel(channelService.findByName(channel), 100, 0));
    }

}
