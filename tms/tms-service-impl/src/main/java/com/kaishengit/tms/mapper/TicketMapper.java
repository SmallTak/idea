package com.kaishengit.tms.mapper;

import com.kaishengit.tms.entity.Ticket;
import com.kaishengit.tms.entity.TicketExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TicketMapper {
    long countByExample(TicketExample example);

    int deleteByExample(TicketExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ticket record);

    int insertSelective(Ticket record);

    List<Ticket> selectByExample(TicketExample example);

    Ticket selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ticket record, @Param("example") TicketExample example);

    int updateByExample(@Param("record") Ticket record, @Param("example") TicketExample example);

    int updateByPrimaryKeySelective(Ticket record);

    int updateByPrimaryKey(Ticket record);

    void batchInsert(@Param("tickets") List<Ticket> tickets);

    void batchUpdate(@Param("ticketList") List<Ticket> ticketList);

    List<Ticket> findByBeginNumAndEndNum(@Param("beginNum") String beginTicketNum, @Param("endNum") String endTicketNum);

    void batchDeleteById(@Param("idList") List<Long> idList);

    Map<String,Long> countByState();

    List<Ticket> selectByStroeIdAndTicketStatu(Integer id);

    void batchUpdateState(@Param("ticketList") List<Ticket> outTimeTicket,@Param("state") String ticketStateOutDate);
}