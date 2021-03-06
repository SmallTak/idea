<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>TMS | 新增售票点</title>
    <%@include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/uploader/webuploader.css">
    <style>
        .photo {
            width: 100%;
            height: 300px;
            border: 2px dashed #ccc;
            margin-top: 20px;
            text-align: center;
            line-height: 300px;
        }
    </style>
</head>
<body class="hold-transition skin-black-light sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../include/navhead.jsp"%>

    <!-- =============================================== -->

    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="ticket_store"/>
    </jsp:include>


    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->

        <section class="content-header">
            <h1>
                新增售票点
            </h1>
        </section>

           <div class="box-header">
               <div class="box-tools">
                   <a href="/ticketstore" class="btn btn-default btn-sm">返回</a>
               </div>
           </div>

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-body">
                    <form method="post" id="saveForm">
                        <input type="hidden" id="storeManagerAttachment" name="stroeManagerAttachment">
                        <input type="hidden" id="storeAttachment" name="stroeAttachment">
                        <div class="form-group">
                            <label>售票点名称</label>
                            <input type="text" class="form-control" name="stroeName">
                        </div>
                        <div class="form-group">
                            <label>售票点地址</label>
                            <input type="text" class="form-control" name="stroeAddress">
                        </div>
                        <div class="form-group">
                            <label>联系人</label>
                            <input type="text" class="form-control" name="stroeManager">
                        </div>
                        <div class="form-group">
                            <label>联系电话</label>
                            <input type="text" class="form-control" name="stroeMobile">
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div id="picker">选择联系人身份证照片</div>
                                <div class="photo" id="userPhoto"></div>
                            </div>
                            <div class="col-md-6">
                                <div id="picker2">选择营业执照照片</div>
                                <div class="photo" id="storePhoto"></div>
                            </div>
                        </div>

                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary pull-right" id="saveBtn">保存</button>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/uploader/webuploader.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>


<script>

    $(function () {

        $("#saveBtn").click(function () {
            $("#saveForm").submit();
        });

        var uploader = WebUploader.create({

            // 选完文件后，是否自动上传。
            auto: true,

            // swf文件路径
            swf: '/static/plugins/uploader/Uploader.swf',

            // 文件接收服务端。
            server: 'http://upload-z2.qiniup.com',
            fileVar:'file',
            formData:{
                "token":"${token}"
            },
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#picker',

            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        //文件上传进度条
        var index = -1;
        uploader.on( 'uploadStart', function( file ) {
            index = layer.load(1);
        });


        uploader.on( 'uploadSuccess', function( file ,response) {
            $("#userPhoto").html("");
            //获得七牛返回json对象中的key值
           var fileName = response.key;
           var $img = $("<img>").attr("src","http://p7ktaebxa.bkt.clouddn.com/"+fileName+"-save");
           $img.appendTo($("#userPhoto"))

           $("#storeManagerAttachment").val(fileName);
            layer.msg("上传成功")

        });

        uploader.on( 'uploadError', function( file ) {
            layer.msg("服务器繁忙");
        });

        uploader.on( 'uploadComplete', function( file ) {
            layer.close(index);
        });

        var uploader2 = WebUploader.create({

            // 选完文件后，是否自动上传。
            auto: true,

            // swf文件路径
            swf: '/static/plugins/uploader/Uploader.swf',

            // 文件接收服务端。
            server: 'http://upload-z2.qiniup.com',
            fileVar:'file',
            formData:{
                "token":"${token}"
            },
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#picker2',

            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        //文件上传进度条
        var index = -1;
        uploader2.on( 'uploadStart', function( file ) {
            index = layer.load(1);
        });


        uploader2.on( 'uploadSuccess', function( file, response ) {
            $("#storePhoto").html("");
            var stroreName = response.key;
            var $img = $("<img>").attr("src","http://p7ktaebxa.bkt.clouddn.com/"+stroreName+"-save");
            $img.appendTo("#storePhoto");
            $("#storeAttachment").val(stroreName);
            layer.msg("上传成功");

        });

        uploader2.on( 'uploadError', function( file ) {
            layer.msg("服务器繁忙");
        });

        uploader2.on( 'uploadComplete', function( file ) {
            layer.close(index);
        });


    });

</script>


</body>
</html>