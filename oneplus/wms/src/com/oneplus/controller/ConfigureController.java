package com.oneplus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oneplus.constant.Const;
import com.oneplus.model.Configure;
import com.oneplus.service.ConfigureService;

/**
* 功能描述：
*
* @author: Zhenbin.Li
* email： lizhenbin@oneplus.cn
* company：一加科技
* Date: 15/6/12 Time：23:41
*/
@Controller
public class ConfigureController {

    /** sl4j */
	private Logger log = Logger.getLogger(ConfigureController.class);
	
	@Autowired
	private ConfigureService configureService;
	
	@RequestMapping(value="/configure/lis")
    public String list(ModelMap modelMap){
		try {
			List<Configure> list = configureService.getConfigureList();
			request.setAttribute("list", list);
		} catch (Exception e) {
			log.error(e);
		}
        return "configure";
    }
	
	@RequestMapping(value="/configure/get")
	@ResponseBody
    public Map<String,Object> get(@RequestParam("Id") Integer Id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Configure configure = configureService.getConfigureByPrimaryKey(Id);
            map.put("configure", configure);
			map.put(Const.STATUS, Const.SUCCESS);
		} catch (Exception e) {
			log.error(e);
		}
        return map;
    }
    
    @RequestMapping(value="/configure/create")
	@ResponseBody
    public Map<String,Object> create(@ModelAttribute Configure configure){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			configureService.createConfigure(configure);
			map.put("configure", configure);
			map.put(Const.STATUS, Const.SUCCESS);
		} catch (Exception e) {
			log.error(e);
			map.put(Const.STATUS, Const.FAILURE);
			map.put(Const.ERROR_MESSAGE, Const.CREATE_EXCEPTION);
		}
        return map;
    }

    @RequestMapping(value="/configure/update")
	@ResponseBody
    public Map<String,Object> update(@ModelAttribute Configure configure){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Configure oldConfigure = configureService.getConfigureByPrimaryKey(configure.get${PrimaryKey}());
            ${updateAssignValue}
			configureService.updateConfigure(oldConfigure);
			map.put("configure", configure);
			map.put(Const.STATUS, Const.SUCCESS);
		} catch (Exception e) {
			log.error(e);
			map.put(Const.STATUS, Const.FAILURE);
			map.put(Const.ERROR_MESSAGE, Const.UPDATE_EXCEPTION);
		}
        return map;
    }

    @RequestMapping(value="/configure/delete")
	@ResponseBody
    public Map<String,Object> delete(@RequestParam("deleteId") Integer Id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			configureService.deleteConfigure(Id);
			map.put(Const.STATUS, Const.SUCCESS);
		} catch (Exception e) {
			log.error(e);
			map.put(Const.STATUS, Const.FAILURE);
			map.put(Const.ERROR_MESSAGE, Const.DELETE_EXCEPTION);
		}
        return map;
    }
}