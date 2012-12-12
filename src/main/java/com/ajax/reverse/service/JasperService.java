package com.ajax.reverse.service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JasperService {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private MessageService messageService;

    public JRDataSource getDataSourceForChannel(String channel) {
        return new JRBeanCollectionDataSource(messageService.findMessagesByChannel(channelService.findByName(channel), 100, 0));
    }
}
