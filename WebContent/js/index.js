$(document).ready(function() {
	$("#get_bundle_btn").click(function() {
		var description = $("#description").val();
		var input = $("#input").val().replace(" ", "");
		var output = $("#output").val().replace(" ", "");
		var msg = $("#bundle_msg");

		var reg = /^((\w+)(,(\w+))*)?$/;
		if (!reg.test(input)) {
			msg.html("input format error: " + input);
		} else if (!reg.test(output)) {
			msg.html("output format error: " + output);
		} else {
			var value = new Object();
			value.description = description;
			value.input = input == "" ? [] : input.split(",");
			value.output = output == "" ? [] : output.split(",");
			value = JSON.stringify(value);
			msg.html(value);
			$.ajax({
				url : "BundleServlet",
				data : {
					"service_description" : value
				},
				dataType : 'JSON',
				type : "POST",
				success : function(data) {
					$("#bundle_result").html(JSON.stringify(data));
				}
			});
		}
	});

	$("#list_template_btn").click(function() {
		$.ajax({
			url : "ListTemplateServlet",
			success : function(data) {
				$("#list_template_result").html(JSON.stringify(data));
			}
		});
	});

	$("#get_template_btn").click(function() {
		var reg = /^(\w+)\.jar$/;
		var template = $("#template_name").val();
		if (reg.test(template)) {
			var data = new Object();
			data.template = template;
			$.ajax({
				url : "TemplateServlet",
				type : "POST",
				data : data,
				dataType : "JSON",
				success : function(data) {
					$("#template_result").html(JSON.stringify(data));
				}
			});
		}
	});
});