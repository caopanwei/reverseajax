package com.ajax.reverse.service.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ajax.reverse.dao.ChannelRepository;
import com.ajax.reverse.domain.Channel;
import com.ajax.reverse.service.ChannelService;
import com.ajax.reverse.service.MessageService;

@ContextConfiguration(locations = {"/TEST-INF/root-context.xml"})
public class ChannelServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    ChannelService underTest;

    @Mock
    ChannelRepository channelRepository;
    @Mock
    MessageService messageService;
    @Mock
    Channel channel;

    @BeforeClass
    void beforeClass() {
        MockitoAnnotations.initMocks(this);
        underTest.setChannelRepository(channelRepository);
        underTest.setMessageService(messageService);
    }

    @BeforeMethod
    void beforeMethod() {
        Mockito.reset(channelRepository, messageService);
    }

    @Test
    void testGetChannelListForCaching() {
        //GIVEN
        List<Channel> list = Arrays.asList(channel);
        BDDMockito.given(channelRepository.getAll()).willReturn(list);
        //WHEN
        Collection<Channel> channelList1 = underTest.getChannelList();
        Collection<Channel> channelList2 = underTest.getChannelList();
        //THEN
        BDDMockito.verify(channelRepository, new Times(1)).getAll();
        Assert.assertEquals(channelList1, list);
        Assert.assertEquals(channelList2, list);
    }

    @Test
    void testCreateIfItErasesTheCache() {
        //GIVEN
        List<Channel> list = Arrays.asList(channel);
        BDDMockito.given(channelRepository.getAll()).willReturn(list);
        BDDMockito.given(channelRepository.create(BDDMockito.anyString())).willReturn(channel);
        //WHEN
        Collection<Channel> channelList1 = underTest.getChannelList();
        Channel newChannel = underTest.create("test");
        list.add(newChannel);
        Collection<Channel> channelList2 = underTest.getChannelList();
        //THEN
        Assert.assertEquals(channelList1, list);
        Assert.assertEquals(newChannel, channel);
        Assert.assertEquals(channelList2, list);
    }
   
}
