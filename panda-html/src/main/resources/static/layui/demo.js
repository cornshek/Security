/*
 steps组件
 */
layui.use(['jquery', 'steps'], function(){

    var $ = layui.$;

    var $step= $("#step_demo").step();

    $("#preBtn").click(function(event) {
         //判断当前的输入是否全部填入
         if(true){
            $step.preStep();//上一步
            //将一个页面的嵌套一个div中
            //获取当前页面的src值
            var srcVal = $("#iframeMain").attr("src");
            // var rs = parseInt((srcVal.replace(/[^0-9]/ig, ""))) -1;
            var rs = parseInt(srcVal) -1;
            if(rs == 0){
                $("#iframeMain").attr("src","1");
                $("#preBtn").attr("disabled","disabled ")
                return
            }
            $("#nextBtn").removeAttr("disabled")
            // var ht = "./"+rs+".html";
            $("#iframeMain").attr("src",rs)
           
        }else{
            alert("请将信息填完整")
        }
        
    });
    $("#nextBtn").click(function(event) {
        //判断当前的输入是否全部填入
        if(true){
            $step.nextStep();//下一步
            //将一个页面的嵌套一个div中
            $("#div1").empty()
            //获取当前页面的src值
            var srcVal = $("#iframeMain").attr("src");
            // var rs = parseInt((srcVal.replace(/[^0-9]/ig, ""))) +1 ;
            var rs = parseInt(srcVal) +1 ;
            if(rs == 7){
                $("#iframeMain").attr("src","7")
                $("#nextBtn").attr("disabled","disabled ")
                return
            }
            $("#preBtn").removeAttr("disabled")
            // var ht = "./"+rs+".html";
            $("#iframeMain").attr("src",rs)
           
        }else{
            alert("请将信息填完整")
        }
    });
});
