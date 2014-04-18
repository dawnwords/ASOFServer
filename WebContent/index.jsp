<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Bundle Servlet</title>
</head>
<body>
	<script type="text/javascript">
		window.onload = function() {
			document.getElementById("submit_btn").onclick = function() {
				var description = getInputValue("description");
				var input = getInputValue("input").replace(" ", "");
				var output = getInputValue("output").replace(" ", "");
				var service_description = document
						.getElementById("service_description");
				var msg = document.getElementById("msg");

				var reg = /^((\w+)(,(\w+))*)?$/;
				if (!reg.test(input)) {
					msg.innerHTML = "input format error: " + input;
				} else if (!reg.test(output)) {
					msg.innerHTML = "output format error: " + output;
				} else {
					var value = new Object();
					value.description = description;
					value.input = input == "" ? [] : input.split(",");
					value.output = output == "" ? [] : output.split(",");

					value = JSON.stringify(value);
					service_description.value = value;
					msg.innerHTML = value;
					document.getElementById("get_bundle").submit();
				}
			};
		}

		function getInputValue(id) {
			return document.getElementById(id).value;
		}
	</script>
	<form id="get_bundle" action="BundleServlet" method="post">
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
		<input id="service_description" name="service_description"
			type="hidden" />
		<p>
			<input id="submit_btn" value="Submit" type="button" /><span id="msg" />
		</p>
	</form>
</body>
</html>