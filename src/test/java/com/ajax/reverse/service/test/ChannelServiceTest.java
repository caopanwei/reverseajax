package com.ajax.reverse.service.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
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

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @BeforeClass
    void beforeClass() {
        MockitoAnnotations.initMocks(this);
        underTest.setChannelRepository(channelRepository);
        underTest.setMessageService(messageService);
    }

    @BeforeMethod
    void beforeMethod() {
        Mockito.reset(channelRepository, messageService, channel);
        underTest.clearCache();
    }

    @Test
    public void testGetChannelListForCaching() {
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
    public void testCreateIfItErasesTheCache() {
        //GIVEN
        List<Channel> list = Arrays.asList(channel);
        BDDMockito.given(channelRepository.getAll()).willReturn(list);
        BDDMockito.given(channelRepository.create(BDDMockito.anyString())).willReturn(channel);
        //WHEN
        underTest.getChannelList();
        Collection<Channel> channelList1 = underTest.getChannelList();
        Channel newChannel = underTest.create("test");
        underTest.getChannelList();
        Collection<Channel> channelList2 = underTest.getChannelList();
        //THEN
        BDDMockito.verify(channelRepository, new Times(2)).getAll();
        BDDMockito.verify(channelRepository).create(stringCaptor.capture());
        Assert.assertEquals(stringCaptor.getValue(), "test");
        Assert.assertEquals(newChannel, channel);
        Assert.assertEquals(channelList1, list);
        Assert.assertEquals(channelList2, list);
    }
    
    @Test
    public void testDeleteIfItErasesCache(){
      //GIVEN
        List<Channel> list = Arrays.asList(channel);
        BDDMockito.given(channelRepository.getAll()).willReturn(list);
        //WHEN
        underTest.getChannelList();
        Collection<Channel> channelList1 = underTest.getChannelList();
        underTest.delete(channel);
        underTest.getChannelList();
        Collection<Channel> channelList2 = underTest.getChannelList();
        //THEN
        BDDMockito.verify(channelRepository, new Times(2)).getAll();
        Assert.assertEquals(channelList1, list);
        Assert.assertEquals(channelList2, list);
    }

}
