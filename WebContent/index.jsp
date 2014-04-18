<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<title>Test</title>
</head>
<body>

	<table border="1">
		<tr>
			<td>
				<table>
					<tbody>
						<tr>
							<td>Description:</td>
							<td><input id="description" type="text" /></td>
						</tr>
						<tr>
							<td>Input:</td>
							<td><input id="input" type="text" /></td>
						</tr>
						<tr>
							<td>Output:</td>
							<td><input id="output" type="text" /></td>
						</tr>
					</tbody>
				</table>
				<p>
					<input id="get_bundle_btn" value="Submit" type="button" /> <span
						id="bundle_msg" />
				</p>
			</td>
			<td id="bundle_result"></td>
		</tr>
		<tr>
			<td>List Templates:<input id="list_template_btn" value="Submit"
				type="button" />
			</td>
			<td id="list_template_result"></td>
		</tr>
		<tr>
			<td>Template Name:<input id="template_name" name="template"
				type="text" /><input id="get_template_btn" value="Submit"
				type="submit" /></td>
			<td id="template_result"></td>
		</tr>
	</table>

</body>
</html>