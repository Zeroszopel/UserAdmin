<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Country Management </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="Responsive bootstrap 4 admin template" name="description">
        <meta content="Coderthemes" name="author">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- App favicon -->
        <link rel="shortcut icon" href="admin\assets\images\favicon.ico">
        <!-- App css -->
        <link href="admin\assets\css\bootstrap.min.css" rel="stylesheet" type="text/css" id="bootstrap-stylesheet">
        <link href="admin\assets\css\icons.min.css" rel="stylesheet" type="text/css">
        <link href="admin\assets\css\app.min.css" rel="stylesheet" type="text/css" id="app-stylesheet">
    <link rel="stylesheet" href="font-awesome-4.7.0/css/font-awesome.min.css">
    
</head>
<body>
 <div id="wrapper">
<div class="navbar-custom">
                <ul class="list-unstyled topnav-menu float-right mb-0">

                    <li class="dropdown d-none d-lg-block">
                        <a class="nav-link dropdown-toggle mr-0" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                            <img src="admin\assets\images\flags\us.jpg" alt="user-image" class="mr-2" height="12"> <span class="align-middle">English <i class="mdi mdi-chevron-down"></i> </span>
                        </a>
                        <div class="dropdown-menu dropdown-menu-right">
                             <!-- item-->
                             <!-- <a href="javascript:void(0);" class="dropdown-item notify-item">
                                    <img src="admin\assets\images\flags\spain.jpg" alt="user-image" class="mr-2" height="12"> <span class="align-middle">Spanish</span>
                             </a> -->

                        </div>
                    </li>

                    

                    <li class="dropdown notification-list">
                        <a class="nav-link dropdown-toggle nav-user mr-0" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                            <img src="admin/images/profile.jpg" alt="user-image" class="rounded-circle">
                            <span class="pro-user-name ml-1">
                                    Tran Nhu Hoang  <i class="mdi mdi-chevron-down"></i> 
                            </span>
                        </a>
                        <div class="dropdown-menu dropdown-menu-right profile-dropdown ">
                            <!-- item-->
                            <div class="dropdown-header noti-title">
                                <h6 class="text-overflow m-0">Welcome !</h6>
                            </div>

                            <!-- item-->
                            <a href="javascript:void(0);" class="dropdown-item notify-item">
                                <i class="mdi mdi-account-outline"></i>
                                <span>Profile</span>
                            </a>
				<!-- 
                            item
                            <a href="javascript:void(0);" class="dropdown-item notify-item">
                                <i class="mdi mdi-settings-outline"></i>
                                <span>Settings</span>
                            </a>

                            item
                            <a href="javascript:void(0);" class="dropdown-item notify-item">
                                <i class="mdi mdi-lock-outline"></i>
                                <span>Lock Screen</span>
                            </a>
				 -->
                            <div class="dropdown-divider"></div>

                            <!-- item-->
                            <a href="javascript:void(0);" class="dropdown-item notify-item">
                                <i class="mdi mdi-logout-variant"></i>
                                <span>Logout</span>
                            </a>

                        </div>
                    </li>


                </ul>

                <!-- LOGO -->
                <div class="logo-box">
                    <a href="index.html" class="logo text-center logo-dark">
                        <span class="logo-lg">
                            <img src="admin\assets\images\logo-dark.png" alt="" height="26">
                            <!-- <span class="logo-lg-text-dark">Simple</span> -->
                        </span>
                        <span class="logo-sm">
                            <!-- <span class="logo-lg-text-dark">S</span> -->
                            <img src="admin\assets\images\logo-sm.png" alt="" height="22">
                        </span>
                    </a>

                    <a href="index.html" class="logo text-center logo-light">
                        <span class="logo-lg">
                            <img src="admin\assets\images\logo-light.png" alt="" height="26">
                            <!-- <span class="logo-lg-text-light">Simple</span> -->
                        </span>
                        <span class="logo-sm">
                            <!-- <span class="logo-lg-text-light">S</span> -->
                            <img src="admin\assets\images\logo-sm.png" alt="" height="22">
                        </span>
                    </a>
                </div>

                <ul class="list-unstyled topnav-menu topnav-menu-left m-0">
                    <li>
                        <button class="button-menu-mobile">
                            <i class="mdi mdi-menu"></i>
                        </button>
                    </li>
        
                    <li class="d-none d-sm-block">
                        <form class="app-search">
                            <div class="app-search-box">
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Search...">
                                    <div class="input-group-append">
                                        <button class="btn" type="submit">
                                            <i class="fas fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </li>
                </ul>
            </div>
            <div class="left-side-menu">


                <div class="user-box">
                        <div class="float-left">
                            <img src="admin/images/profile.jpg" alt="" class="avatar-md rounded-circle">
                        </div>
                        <div class="user-info">
                            <a href="#">Tran Nhu Hoang</a>
                            <p class="text-muted m-0">Administrator</p>
                        </div>
                    </div>
    
            <!--- Sidemenu -->
            <div id="sidebar-menu">
    
                <ul class="metismenu" id="side-menu">
    
                    <li class="menu-title">Navigation</li>
    
                    <li>
                        <a href="index.html">
                            <i class="ti-home"></i>
                            <span> Main </span>
                        </a>
                    </li>
    
    
                    <li>
                        <a href="javascript: void(0);">
                            <i class="ti-menu-alt"></i>
                            <span>  List </span>
                            <span class="menu-arrow"></span>
                        </a>
                        <ul class="nav-second-level" aria-expanded="false">
                            <li><a href="/users">Users</a></li>
                            <li><a href="/products">Products</a></li>
                            <li><a href="/country">Country</a></li>
                            <li><a href="/category">Products</a></li>
                        </ul>
                    </li>
    
                    
                </ul>
    
            </div>
            <!-- End Sidebar -->
    
            <div class="clearfix"></div>

    
    </div>
<div class="content-page">
<div class="content">
<div class="container-fluid">
<div align="center">
    <h1>Country Management</h1>
    <h2>
        <a href="/country?action=create">Add New Country</a>
    </h2>
</div>   
<div align="center">
	<form method="get" >
		<input type="text" name="search" value="${search}" size="100">
		<input type="submit" value="Search"/>
	</form>
</div>
<div align="center">
    <table border="1" cellpadding="5">
        <h2>List of Country</h2>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="country" items="${listCountry}">
            <tr>
                <td><c:out value="${country.getId()}"/></td>
                <td><c:out value="${country.getName()}"/></td>
                <td>
                    <a href="/country?action=edit&id=${country.getId()}">Edit</a>
                    <a href="/country?action=delete&id=${country.getId()}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <table border="1" cellpadding="5" cellspacing="5">
    
        <tr>
        	<c:if test="${currentPage != 1}">
        		<th><a href="country?<c:if test="${not empty search}">search=${search}&</c:if>page=${currentPage - 1}"><i class="fa fa-chevron-left" aria-hidden="true"></i></a></th>
    		</c:if>
            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="country?<c:if test="${not empty search}">search=${search}&</c:if>page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        
        	<c:if test="${currentPage lt noOfPages}">
        		<th><a href="country?<c:if test="${not empty search}">search=${search}&</c:if>page=${currentPage + 1}"><i class="fa fa-chevron-right" aria-hidden="true"></i></a></th>
    		</c:if>
    	</tr>
    </table>
</div>
</div>
</div>
</div>
<footer class="footer">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-12">
                                    2017 - 2020 &copy; Simple theme by <a href="">Coderthemes</a>
                                </div>
                            </div>
                        </div>
                    </footer>
                    <!-- end Footer -->
                    
</div>

        

        <!-- Vendor js -->
        <script src="admin\assets\js\vendor.min.js"></script>

        <script src="admin\assets\libs\morris-js\morris.min.js"></script>
        <script src="admin\assets\libs\raphael\raphael.min.js"></script>

        <script src="admin\assets\js\pages\dashboard.init.js"></script>

        <!-- App js -->
        <script src="admin\assets\js\app.min.js"></script>

</body>
</html>