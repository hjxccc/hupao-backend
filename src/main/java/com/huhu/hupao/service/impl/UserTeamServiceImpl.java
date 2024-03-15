package com.huhu.hupao.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huhu.hupao.mapper.TeamMapper;
import com.huhu.hupao.mapper.UserTeamMapper;
import com.huhu.hupao.model.domain.Team;
import com.huhu.hupao.model.domain.UserTeam;
import com.huhu.hupao.service.ITeamService;
import com.huhu.hupao.service.IUserTeamService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户队伍关系 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-02-03
 */
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam> implements IUserTeamService {

}
