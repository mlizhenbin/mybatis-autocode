<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html lang="zh-cn">
<%@ page isELIgnored="false" %>

<head>
    <title>ConfigDict详情</title>
    <%@include file="/v/wmsCommon/public_head.jsp" %>
    <script src="/v/js/common/wms.upload.js"></script>
    <script src="/v/js/common/wms.jquery.extend.js"></script>
</head>

<body>
    <div class="in-order-detail-title">
        <span class="p-ladder-shaped-down">采购单抬头</span>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="100" height="30" align="right"> 采购单号：</td>
                <td width="150">HB201604221106511048</td>
                <td width="100" align="right"> 采购类型：</td>
                <td width="100">换货补货
                </td>
                <td width="100" align="right"> 供应商：</td>
                <td style="width: 240px;">F810鹦鹉螺网络科技（北京）有限公司</td>
            </tr>
            <tr>
                <td width="100" height="30" align="right"> 采购人：</td>
                <td>李真斌</td>
                <td width="100" align="right"> 商品总数：</td>
                <td>450</td>
                <td align="right"> 已收货数：</td>
                <td>205</td>
            </tr>
            <tr style="display: none;">
                <td width="100" height="30" align="right"> 预计到货日期：</td>
                <td colspan="3"></td>
                <td width="100" align="right"> 实际到货日期：</td>
                <td colspan="3"></td>
            </tr>
            <tr>
                <td width="100" align="right"> 下单日期：</td>
                <td>2016-04-22</td>
                <td width="100" height="30" align="right"> 摘要：</td>
                <td colspan="5">测试测试测试</td>
            </tr>
        </table>
    </div>

    <div class="in-order-datail-list-title p-ladder-shaped-up">
        <span>采明细</span>
    </div>

    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="p-table in-order-datail-list">
        <tr class="p-table-header">
            <td width="30">序号</td>
            <td width="80">物料代码</td>
            <td width="100">物料描述</td>
            <td width="100">物料类型</td>
            <td width="50">下单数量</td>
            <td width="80">已收货数</td>
            <td width="50">单位</td>
        </tr>
        <tr>
            <td>1</td>
            <td>0602001801</td>
            <td>5017111快递盒14001 快递专用 小</td>
            <td></td>
            <td>150</td>
            <td>105</td>
            <td>PCS</td>
        </tr>
    </table>

    <div class="p-center">
        <input class="l-button" type="button" value="返回" onclick="window.history.back();"/>
    </div>
</body>
<html>