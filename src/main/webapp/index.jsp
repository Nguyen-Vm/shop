<%@ page contentType="text/html;charset=gb2312" %>
<html>
<body>
<h2>Tomcat 1!</h2>
<h2>Tomcat 1!</h2>
<h2>Tomcat 1!</h2>
<h2>Hello World!</h2>

springmvc�ϴ��ļ���FTP
<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="�ϴ��ļ���FTP" />
</form>

springmvc�ϴ��ļ���OSS
<form name="form1" action="/manage/product/upload_oss.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="�ϴ��ļ���OSS" />
</form>


���ı�ͼƬ�ϴ��ļ�
<form name="form2" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" />
    <input type="submit" value="���ı�ͼƬ�ϴ��ļ�" />
</form>
</body>
</html>
