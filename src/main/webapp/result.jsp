<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, width=device-width">
<link rel='stylesheet'
	href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
<title>G-Lint</title>
</head>
<body>



	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">G-Lint</a>
			</div>
		</div>
	</nav>


	<div class="container">
        <div id="result">
            <h3>${requestScope["message"]}</h3>
        </div>
	</div>


	<div class="container">
		<footer class="footer navbar-fixed-bottom">
			<div class="row text-muted">
				<div class="col-sm-8">&copy; 2016 Matthew R. Harrah</div>
				<div class="col-sm-4 text-right">
					Powered by <a href="http://gedcom4j.org">gedcom4j</a>
				</div>
			</div>
		</footer>
	</div>




	<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="webjars/bootstrap-filestyle/1.1.2/bootstrap-filestyle.js"></script>
	</script>
</body>
</html>