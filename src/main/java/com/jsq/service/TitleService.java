package com.jsq.service;

import com.jsq.entity.TitleHead;
import com.jsq.entity.TitleLine;
import com.jsq.entity.TitleScore;
import com.jsq.mapper.TitleHeadMapper;
import com.jsq.mapper.TitleScoreMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Service
@Transactional
public class TitleService extends BaseService<TitleHeadMapper,TitleHead> {
    @Autowired
    private TitleHeadMapper titleHeadMapper;
    @Autowired
    private TitleScoreMapper titleScoreMapper;

    private static final String path = "D:\\jsq\\graduation-design-spring\\src\\main\\resources\\templates\\title";

    //插入题目头信息
    public TitleHead insertHead (TitleHead t){
        File file = new File(path,t.getTitleName()+".txt");
        t.setUrl(path);
        titleHeadMapper.insert(t);
        try{
            file.createNewFile();
        }catch (Exception e){
            System.out.print(e);
        }
        return t;
    }

    //添加题目行信息
    public TitleLine addTitleLine (TitleLine t, Long id){
        String titleName = titleHeadMapper.selectOne(TitleHead.builder().id(id).enabled(true).build()).getTitleName();
        File file = new File(path,titleName+".txt");
        t.setUuid(UUID.randomUUID());
        List<TitleLine> list = getInfo(false,id);
        list.add(t);
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(list);
            out.flush();
            out.close();
        }catch (Exception e){
            System.out.print(e);
        }
        return  t;
    }

    //删除题目
    public boolean deleteLine(UUID uuid,Long id){
        String titleName = titleHeadMapper.selectOne(TitleHead.builder().id(id).enabled(true).build()).getTitleName();
        File file = new File(path,titleName+".txt");

        List<TitleLine> list = getInfo(false,id);

        TitleLine titleLine = null;
        for(TitleLine t : list){
            if(t.getUuid().equals(uuid)){
                titleLine = t;
            }
        }
        if(titleLine!=null)
            list.remove(titleLine);
        try{
            System.out.print(1);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(list);
            out.flush();
            out.close();
        }catch (Exception e){
            System.out.print(e);
        }
        return true;
    }

    //查询题目行信息
    public List<TitleLine> getInfo (boolean flag,Long id){
        String titleName = titleHeadMapper.selectOne(TitleHead.builder().id(id).enabled(true).build()).getTitleName();
        File file = new File(path,titleName+".txt");

        List<TitleLine> list = new ArrayList<>();
        Object temp = null;

        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            temp = in.readObject();
            if(temp instanceof List){
                list = (List<TitleLine>)temp;
            }
            in.close();
        }catch (Exception e){
            return list;
        }
        if(flag){
            list.stream().map(titleLine -> {
                titleLine.setAnswer(null);
                return titleLine;
            }).collect(Collectors.toList());
        }
        return  list;
    }

    //评分
    public boolean getScore(List<TitleLine> titleScoreList,Long headId,String userNumber){
        String titleName = titleHeadMapper.selectOne(TitleHead.builder().id(headId).enabled(true).build()).getTitleName();
        File file = new File(path,titleName+".txt");
        List<TitleLine> list = getInfo(false,headId);

        double score = 0;

        for(TitleLine titleLine : list){
            for(TitleLine titleScore : titleScoreList){
                if(titleLine.getUuid().equals(titleScore.getUuid())&&titleLine.getAnswer().equals(titleScore.getAnswer())){
                    if(titleLine.getRate()==0)
                        score+= 2;
                    else
                        score+=titleLine.getRate();
                }
            }
        }

        titleScoreMapper.insert(TitleScore.builder().score(score).titleHId(headId).userNumber(userNumber).build());

        return true;
    }

}
