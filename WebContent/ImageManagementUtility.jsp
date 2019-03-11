<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.nagarro.assignment.dao.pojo.User"%>
<%@ page import="com.nagarro.assignment.dao.pojo.Image"%>
<%@ page import="java.lang.Math"%>
<%@ page import="java.text.DecimalFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" href="css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>


<script language="javascript">
	function reset() {
		document.getElementById("filename").value = "";
	}
</script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
	<div style="text-align: center">
		<h1>Image Management Utility</h1>
	</div>
	<br>
	<br>

	<div>
		<h3>Please Select An Image File To Upload(Max Size 1MB)</h3>
		<form method="post" enctype="multipart/form-data" action="Submitting">
			<input type="file" name="file" id="filename" size="50" /> <input
				type="submit" value="Upload File" align="right" />
		</form>
		<p>${msg}</p>
		<button type="button" id="filename" onclick="reset();">Cancel
		</button>
		<br>
		<%
			List<Image> list = (List<Image>) request.getAttribute("imagesList");
			int size = list.size();
			if (size != 0) {
		%>

		<table class="table">
			<thead class="thead-dark">

				<tr>
					<th scope="col">S.NO</th>
					<th scope="col">NAME</th>
					<th scope="col">SIZE</th>
					<th scope="col">PEVIEW</th>
					<th scope="col">ACTION</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (int i = 0; i < size; i++) {
							Image image = list.get(i);
							DecimalFormat decimalFormatter = new DecimalFormat("#.##");
				%>
				<tr>
					<th scope="row"><%=i + 1%></th>
					<td><%=image.getName()%></td>
					<td>
						<%=decimalFormatter.format(new Double(image.getSize())/1024)%>KB
					</td>
					<td><img alt="No Image" src="<%=image.getPreview()%>"
						class="img-thumbnail" height="100px" width="40%"></td>
					<td><a
						href="DeleteAndEditBook?imageId=<%=image.getImageId()%>">Delete</a>
						<button type="button" class="btn btn-info btn-lg" name="edit"
							data-toggle="modal" data-target="#<%=image.getImageId()%>">Editing</button>
						<form action="EditBook" method="post"
							enctype="multipart/form-data">
							<div class="modal fade" id="<%=image.getImageId()%>"
								role="dialog">
								<div class="modal-dialog modal-lg">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
										</div>
										<div class="modal-body">

											<label for="formGroupExampleInput">Image Name</label> <input
												type="text" class="form-control" id="formGroupExampleInput"
												name="name" placeholder="Enter Name For Book"> <br>
											<input type="file" name="file" id="file" size="50" />


										</div>

										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">Close</button>
											<button type="submit" name="submit"
												value="<%=image.getImageId()%>">Update</button>
										</div>
									</div>
								</div>
							</div>
						</form>
				</tr>
				<%
					}
					}
				%>
			</tbody>
		</table>

	</div>
</body>
</html>