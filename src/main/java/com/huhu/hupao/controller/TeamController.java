package com.huhu.hupao.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huhu.hupao.common.BaseResponse;
import com.huhu.hupao.common.ErrorCode;
import com.huhu.hupao.common.ResultUtils;
import com.huhu.hupao.exception.BusinessException;
import com.huhu.hupao.model.domain.Team;
import com.huhu.hupao.model.domain.User;
import com.huhu.hupao.model.domain.UserTeam;
import com.huhu.hupao.model.domain.request.TeamAddRequest;
import com.huhu.hupao.model.domain.request.TeamJoinRequest;
import com.huhu.hupao.model.domain.request.TeamQuitRequest;
import com.huhu.hupao.model.domain.request.TeamUpdateRequest;
import com.huhu.hupao.model.dto.TeamQuery;
import com.huhu.hupao.model.vo.TeamUserVO;
import com.huhu.hupao.service.ITeamService;
import com.huhu.hupao.service.IUserTeamService;
import com.huhu.hupao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 队伍 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-02-02
 */
@RestController
@RequestMapping("/team")
@CrossOrigin(origins = {"http://localhost:8080"})
@Slf4j
public class TeamController {

    @Resource
    private UserService userService;
    @Resource
    private ITeamService teamService;

    @Resource
    private IUserTeamService userTeamService;


    @PostMapping("/add")
    public BaseResponse<Long> addTeam(@RequestBody TeamAddRequest teamAddRequest, HttpServletRequest request){
        if(teamAddRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser=userService.getLogininUser(request);
        Team team=new Team();
        BeanUtils.copyProperties(teamAddRequest,team);
        long teamId = teamService.addTeam(team, loginUser);
        return ResultUtils.success(teamId);
    }

    //解散队伍
    @PostMapping("/delete")
    public BaseResponse<Boolean>  deleteTeam(@RequestBody long id,HttpServletRequest request){
        if (id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User logininUser = userService.getLogininUser(request);
        boolean save=teamService.deleteTeam(id,logininUser);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return ResultUtils.success(true);
    }











//    @PostMapping("/update")
//    public BaseResponse<Boolean> updateTeam(@RequestBody Team team){
//        if(team==null){
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        boolean result=teamService.updateById(team);
//        if(!result){
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新失败");
//        }
//        return ResultUtils.success(result);
//    }
@PostMapping("/update")
public BaseResponse<Boolean> updateTeam(@RequestBody TeamUpdateRequest teamUpdateRequest,HttpServletRequest request){
    if(teamUpdateRequest==null){
        throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    User loginUser = userService.getLogininUser(request);
    boolean result=teamService.updateTeam(teamUpdateRequest,loginUser);
    if(!result){
        throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新失败");
    }
    return ResultUtils.success(true);
}

    @GetMapping("/get")
    public BaseResponse<Team> getTeamById(long id){
        if(id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Team team=teamService.getById(id);
        if(team==null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(team);
    }

/*    @GetMapping("/list")
    public BaseResponse<List<Team>>  listTeams(TeamQuery teamQuery){
            if(teamQuery==null){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
          Team team=new Team();
        try {
            BeanUtils.copyProperties(team,teamQuery);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        QueryWrapper<Team> queryWrapper=new QueryWrapper<>();
        List<Team> teamList = teamService.list(queryWrapper);
        return ResultUtils.success(teamList);
    }*/

    @GetMapping("/list")
    public BaseResponse<List<TeamUserVO>>  listTeams(TeamQuery teamQuery,HttpServletRequest request) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //1.查询队伍列表
        boolean isAdmin = userService.isAdmin(request);
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery, isAdmin);
        //2.判断当前用户是否已加入队伍
        final List<Long> teamIdList = teamList.stream().map(TeamUserVO::getId).collect(Collectors.toList());
        QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();

        try {
            User logininUser = userService.getLogininUser(request);
            userTeamQueryWrapper.eq("userId",logininUser.getId());
            userTeamQueryWrapper.in("teamId",teamIdList);
            List<UserTeam> userTeamList = userTeamService.list(userTeamQueryWrapper);
            //已加入的队伍id集合
            Set<Long> hasJoinTeamIdSet = userTeamList.stream().map(UserTeam::getTeamId).collect(Collectors.toSet());
            teamList.forEach(team ->{
                boolean hasJoin=hasJoinTeamIdSet.contains(team.getId());
                team.setHasJoin(hasJoin);
            });
        }catch (Exception e){
            //3.查询加入队伍的用户信息人数
            QueryWrapper<UserTeam> userTeamJoinQueryWrapper = new QueryWrapper<>();
            userTeamJoinQueryWrapper.in("teamId",teamIdList);
            List<UserTeam> userTeamList=userTeamService.list(userTeamJoinQueryWrapper);
            //队伍id =>加入这个队伍当前用户列表
            Map<Long, List<UserTeam>> teamIdUserTeamList = userTeamList.stream().collect(Collectors.groupingBy(UserTeam::getTeamId));
            teamList.forEach(team ->
                    team.setHasJoinNum(teamIdUserTeamList.getOrDefault(team.getId(), new ArrayList<>()).size()));
            return ResultUtils.success(teamList);
        }
        return ResultUtils.success(teamList);
    }



    @GetMapping("/list/page")
    public BaseResponse<Page<Team>> listTeamsByPage(TeamQuery teamQuery){
     if(teamQuery==null){
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
     }
  Team team=new Team();
  BeanUtils.copyProperties(teamQuery,team);
  Page page=new Page<>(teamQuery.getPageNum(),teamQuery.getPageSize());
  QueryWrapper<Team> queryWrapper=new QueryWrapper<>(team);
  Page<Team> resultPage=teamService.page(page,queryWrapper);
  return ResultUtils.success(resultPage);
    }



    @PostMapping("/join")
    public BaseResponse<Boolean> joinTeam(@RequestBody TeamJoinRequest teamJoinRequest,HttpServletRequest request){
        if(teamJoinRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser=userService.getLogininUser(request);
        boolean result = teamService.joinTeam(teamJoinRequest,loginUser);

        return ResultUtils.success(result);
    }

    @PostMapping("/quit/")
    public BaseResponse<Boolean> quitTeam(@RequestBody TeamQuitRequest teamQuitRequest, HttpServletRequest request){
        if(teamQuitRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser=userService.getLogininUser(request);
        boolean result = teamService.quitTeam(teamQuitRequest,loginUser);

        return ResultUtils.success(result);
    }


    //获取我创建的队伍
    @GetMapping("/list/my/create")
    public BaseResponse<List<TeamUserVO>>  listMyTeams(TeamQuery teamQuery,HttpServletRequest request){
        if(teamQuery==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser=userService.getLogininUser(request);
        boolean isAdmin= userService.isAdmin(loginUser);

        teamQuery.setUserId(loginUser.getId());
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery,true);
        return ResultUtils.success(teamList);
    }
    //获取我加入的队伍
    @GetMapping("/list/my/join")
    public BaseResponse<List<TeamUserVO>>  listMyJoinTeams(TeamQuery teamQuery,HttpServletRequest request){
        if(teamQuery==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User logininUser = userService.getLogininUser(request);
        QueryWrapper<UserTeam> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userId",logininUser.getId());
        List<UserTeam> userTeamList = userTeamService.list(queryWrapper);

        //取出不重复的队伍 id
        //teamId userId
        //1 2
        //1 3
        //2 3
        //result
        // 1=>2 ,3
        //2 => 3
        Map<Long, List<UserTeam>> listMap = userTeamList.stream()
                .collect(Collectors.groupingBy(UserTeam::getTeamId));
        List<Long> idList = new ArrayList<>(listMap.keySet());
        teamQuery.setIdList(idList);
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery,true);
        return ResultUtils.success(teamList);
    }
}
