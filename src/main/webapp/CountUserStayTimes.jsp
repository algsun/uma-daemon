<%--
  Created by IntelliJ IDEA.
  User: xiedeng
  Date: 14-10-20
  Time: 下午4:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="assets/bootstrap/2.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/common-css/0.1.2/common.min.css">
    <script type="text/javascript" src="assets/my97-DatePicker/4.72/WdatePicker.js"></script>
    <script type="text/javascript" src="assets/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript" src="assets/bootstrap/2.3.1/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="assets/bootstrap-hover-dropdown/0bbbb25790/bootstrap-hover-dropdown.min.js"></script>

</head>
<body>


<div id="gcontent" class="container m-t-10">
    <div class="span12">
        <form id="batchForm" class="form-inline" action="UserStayTimeServlet">
            <h3>人员停留时间统计：</h3>

            <div class="control-group">
                <label class="control-label" for="endDate">开始时间</label>
                <input id="startDate" style="height: 30px;cursor: hand" type="text"
                       onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"
                       name="startDate" required readonly="readonly"/>
            </div>
            <div class="control-group">
                <label class="control-label" for="endDate">结束时间</label>
                <input id="endDate" style="height: 30px; cursor: hand" type="text"
                       onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"
                       name="endDate" required readonly="readonly"/>
            </div>

            <button id="commit" class="btn">统计</button>
        </form>
    </div>
</div>
</body>
<script>
    $(function () {
        //验证开始时间和结束时间大小
        var $startDate = $("input[name='startDate']");
        var $endDate = $("input[name='endDate']");

        $startDate.val(formatDate(new Date(), "yyyy-MM-dd"));
        $endDate.val(formatDate(new Date(), "yyyy-MM-dd"));

        var $check = $startDate.popover({
            title: "提示",
            content: "开始时间不能大于结束时间",
            placement: 'right',
            trigger: 'manual'
        });

        $("#commit").click(function () {
            if ($startDate.val() > $endDate.val()) {
                $check.popover('show');
                return false;
            }
            $.get("test.js", function (json) {
                alert("JSON Data: " + json.users[3].name);
            });
        });

        //格式化日期,
        function formatDate(date, format) {
            var paddNum = function (num) {
                num += "";
                return num.replace(/^(\d)$/, "0$1");
            }
            //指定格式字符
            var cfg = {
                yyyy: date.getFullYear() //年 : 4位
                , yy: date.getFullYear().toString().substring(2)//年 : 2位
                , M: date.getMonth() + 1  //月 : 如果1位的时候不补0
                , MM: paddNum(date.getMonth() + 1) //月 : 如果1位的时候补0
                , d: date.getDate()   //日 : 如果1位的时候不补0
                , dd: paddNum(date.getDate())//日 : 如果1位的时候补0
                , hh: date.getHours()  //时
                , mm: date.getMinutes() //分
                , ss: date.getSeconds() //秒
            }
            format || (format = "yyyy-MM-dd hh:mm:ss");
            return format.replace(/([a-z])(\1)*/ig, function (m) {
                return cfg[m];
            });
        }
    });

</script>
</html>
