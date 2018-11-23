<%@ page contentType="text/html;charset=gb2312" %>
<html>
<body>
<h2>Tomcat 1!</h2>
<h2>Tomcat 1!</h2>
<h2>Tomcat 1!</h2>
<h2>Hello World!</h2>

springmvc上传文件至FTP
<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="上传文件至FTP" />
</form>

springmvc上传文件至OSS
<form name="form1" action="/manage/product/upload_oss.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="上传文件至OSS" />
</form>


富文本图片上传文件
<form name="form2" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="富文本图片上传文件" />
</form>
</body>
</html>
