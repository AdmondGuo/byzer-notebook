package io.kyligence.notebook.console.controller;


import io.kyligence.notebook.console.NotebookConfig;
import io.kyligence.notebook.console.bean.dto.*;
import io.kyligence.notebook.console.bean.dto.req.CUConnectionReq;
import io.kyligence.notebook.console.bean.dto.req.SettingsUpdateReq;
import io.kyligence.notebook.console.bean.entity.ConnectionInfo;
import io.kyligence.notebook.console.bean.entity.NodeDefInfo;
import io.kyligence.notebook.console.bean.entity.ParamDefInfo;
import io.kyligence.notebook.console.bean.entity.SystemConfig;
import io.kyligence.notebook.console.service.ConnectionService;
import io.kyligence.notebook.console.service.NodeDefService;
import io.kyligence.notebook.console.service.SystemService;
import io.kyligence.notebook.console.support.DisableInTrial;
import io.kyligence.notebook.console.support.Permission;
import io.kyligence.notebook.console.util.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("api")
@Api("The documentation about operations on settings")
public class SettingsController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private NodeDefService nodeDefService;

    private static final NotebookConfig config = NotebookConfig.getInstance();

    @ApiOperation("Engine List")
    @GetMapping("/settings/engines")
    @Permission
    @DisableInTrial
    public Response<EngineListDTO> getEngineList() {
        EngineListDTO engineListDTO = new EngineListDTO();
        engineListDTO.setList(Arrays.asList("default", "backup"));

        return new Response<EngineListDTO>().data(engineListDTO);
    }

    @ApiOperation("Environment Info")
    @GetMapping("/settings/env")
    public Response<EnvDTO> getEnv() {
        return new Response<EnvDTO>().data(EnvDTO.valueOf(config.getIsTrial(), config.getUserFileSizeLimit()));
    }

    @ApiOperation("Engine List")
    @GetMapping("/settings/configuration")
    @Permission
    @DisableInTrial
    public Response<SystemConfigDTO> getSystemConfig() {
        SystemConfig systemConfig = systemService.getConfig();
        return new Response<SystemConfigDTO>().data(SystemConfigDTO.valueOf(systemConfig));
    }

    @ApiOperation("Rest Engine")
    @PostMapping("/settings/configuration/reset")
    @Permission
    @DisableInTrial
    public Response<SystemConfigDTO> resetSystemConfig() {
        SystemConfig systemConfig = new SystemConfig();

        systemConfig.setTimeout(2880);
        systemService.updateConfig(systemConfig);
        return new Response<SystemConfigDTO>().data(SystemConfigDTO.valueOf(systemConfig));
    }

    @ApiOperation("Get Cipher Key")
    @GetMapping("/settings/key")
    @Permission
    public Response<String> getCipherKey(){
        String keyBytes = systemService.getCipherKey();
        return new Response<String>().data(keyBytes);
    }

    @ApiOperation("Update Config")
    @PutMapping("/settings/configuration")
    @Permission
    @DisableInTrial
    public Response<String> updateSystemConfig(@RequestBody @Validated SettingsUpdateReq settingsUpdateReq) {
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setTimeout(settingsUpdateReq.getTimeout());
        systemConfig.setEngine(settingsUpdateReq.getEngine());
        systemService.updateConfig(systemConfig);

        return new Response<String>().data("update success");
    }

    @ApiOperation("Add Connection")
    @PostMapping("/settings/connection")
    @Permission
    @DisableInTrial
    public Response<IdDTO> addConnection(@RequestBody @Validated CUConnectionReq cuConnectionReq) {
        String user = WebUtils.getCurrentLoginUser();

        ConnectionDTO connectionDTO = cuConnectionReq.getContent();
        connectionDTO.setName(cuConnectionReq.getConnectionName());
        ConnectionInfo connectionInfo = connectionService.createConnection(user, connectionDTO);
        return new Response<IdDTO>().data(IdDTO.valueOf(connectionInfo.getId()));
    }

    @ApiOperation("Add Connection")
    @PostMapping("/settings/connection/test")
    @Permission
    @DisableInTrial
    public Response<String> testConnection(@RequestBody @Validated CUConnectionReq cuConnectionReq) {
        ConnectionDTO connectionDTO = cuConnectionReq.getContent();
        connectionDTO.setName(cuConnectionReq.getConnectionName());
        String msg = connectionService.testConnection(connectionDTO) ? "success" : "error";
        return new Response<String>().data(msg);
    }

    @ApiOperation("Update Connection")
    @PutMapping("/settings/connection/{id}")
    @Permission
    @DisableInTrial
    public Response<IdDTO> updateConnection(@PathVariable("id") @NotNull Integer id,
                                            @RequestBody @Validated CUConnectionReq cuConnectionReq) {
        String user = WebUtils.getCurrentLoginUser();

        ConnectionDTO connectionDTO = cuConnectionReq.getContent();
        connectionDTO.setName(cuConnectionReq.getConnectionName());
        ConnectionInfo connectionInfo = connectionService.updateConnection(id, user, connectionDTO);
        return new Response<IdDTO>().data(IdDTO.valueOf(connectionInfo.getId()));
    }

    @ApiOperation("Delete Connection")
    @DeleteMapping("/settings/connection/{id}")
    @Permission
    @DisableInTrial
    public Response<IdDTO> deleteConnection(@PathVariable("id") @NotNull Integer id) {
        String user = WebUtils.getCurrentLoginUser();
        connectionService.deleteConnection(id, user);
        return new Response<IdDTO>().data(IdDTO.valueOf(id));
    }

    @ApiOperation("Get Connection")
    @GetMapping("/settings/connection/{id}")
    @Permission
    @DisableInTrial
    public Response<ConnectionDTO> getConnection(@PathVariable("id") @NotNull Integer id) {
        ConnectionInfo connectionInfo = connectionService.findById(id);
        return new Response<ConnectionDTO>().data(ConnectionDTO.valueOf(connectionInfo));
    }

    @ApiOperation("Get Connection List")
    @GetMapping("/settings/connection")
    @Permission
    @DisableInTrial
    public Response<ConnectionListDTO> getConnectionList() {
        String user = WebUtils.getCurrentLoginUser();
        List<ConnectionInfo> connectionInfos = connectionService.getConnectionList(user);
        Map<Integer, Boolean> statusMap = connectionService.getConnectionStatus(connectionInfos);
        return new Response<ConnectionListDTO>().data(ConnectionListDTO.valueOf(connectionInfos, statusMap));
    }

    @ApiOperation("Get Connection Tables")
    @GetMapping("/settings/connection/{id}/table")
    @Permission
    @DisableInTrial
    public Response<List<String>> getConnectionTables(@PathVariable("id") @NotNull Integer id) {
        List<String> tables = connectionService.showTables(id);
        return new Response<List<String>>().data(tables);
    }


    @ApiOperation("Get Node Def List")
    @GetMapping("/settings/node/def")
    @Permission
    public Response<NodeDefListDTO> getNodeDefList(@RequestParam(value = "node_type") String nodeType) {
        List<NodeDefInfo> defInfoList = nodeDefService.getNodeDefList(nodeType);
        return new Response<NodeDefListDTO>().data(NodeDefListDTO.valueOf(defInfoList));
    }

    @ApiOperation("Get Node Def")
    @GetMapping("/settings/node/def/{id}")
    @Permission
    public Response<NodeDefDTO> getNodeDef(@PathVariable("id") @NotNull Integer id) {
        NodeDefInfo defInfo = nodeDefService.getNodeDefById(id);
        List<ParamDefInfo> paramDefInfoList = nodeDefService.getParamDefByNodeDefId(id);
        return new Response<NodeDefDTO>().data(NodeDefDTO.valueOf(defInfo, paramDefInfoList));
    }

    @ApiOperation("Get Node Param Def")
    @GetMapping("/settings/node/param")
    @Permission
    public Response<ParamDefDTO> getNodeParamDef(@RequestParam(value = "node_type") String nodeType,
                                                 @RequestParam(value = "node_name") String nodeName,
                                                 @RequestParam(value = "param_name") String paramName) {
        NodeDefInfo nodeDefInfo = nodeDefService.getNode(nodeType, nodeName);
        ParamDefInfo paramDefInfo = nodeDefService.getParamDefByName(nodeDefInfo.getId(), paramName);
        List<ParamDefInfo> allNodeParams = nodeDefService.getParamDefByNodeDefId(nodeDefInfo.getId());
        return new Response<ParamDefDTO>().data(ParamDefDTO.valueOf(paramDefInfo, allNodeParams));
    }
}
