package com.huhu.hupao.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.huhu.hupao.model.domain.Team;
import com.huhu.hupao.model.domain.User;
import com.huhu.hupao.model.domain.request.TeamJoinRequest;
import com.huhu.hupao.model.domain.request.TeamQuitRequest;
import com.huhu.hupao.model.domain.request.TeamUpdateRequest;
import com.huhu.hupao.model.dto.TeamQuery;
import com.huhu.hupao.model.vo.TeamUserVO;

import java.util.List;

/**
 * <p>
 * 队伍 服务类
 * </p>
 *
 * @author author
 * @since 2024-02-02
 */
public interface ITeamService extends IService<Team> {

    //创建队伍
    long addTeam(Team team, User loginUser);


    //搜索队伍
    List<TeamUserVO> listTeams(TeamQuery teamQuery,boolean isAdmin );

    boolean updateTeam(TeamUpdateRequest teamUpdateRequest,User loginUser);

    boolean joinTeam(TeamJoinRequest teamJoinRequest,User loginUser);

    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    boolean deleteTeam(long id,User loginUser);
}
