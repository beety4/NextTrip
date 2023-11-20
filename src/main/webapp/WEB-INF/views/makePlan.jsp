<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<style>
.wConfig75 {
	width : 70%;
}
.wConfig20 {
	width : 20%;
}


</style>


<script src="assets/js/display.js"></script>

<section class="pt-5" style="padding-top: 5rem; margin-top: 100px;">
<div class="container-fluid h-custom wConfig75">
<div class="d-flex" id="wrapper">
            <!-- Sidebar-->
            <div class="border-end bg-white wConfig20" id="sidebar-wrapper">
                <div class="sidebar-heading border-bottom bg-light">Planning</div>
                <div class="list-group list-group-flush">
                    <a class="list-group-item list-group-item-action list-group-item-light p-3 active"
                    	id="Asummary" onclick="showup(this, 'summary');" href="#!">요약</a>
                    <a class="list-group-item list-group-item-action list-group-item-light p-3"
                    	id="Adetails" onclick="showup(this, 'details');" href="#!">상세계획</a>
                    <a class="list-group-item list-group-item-action list-group-item-light p-3"
                    	id="Amap" onclick="showup(this, 'map');" href="#!">지도</a>
                </div>
            </div> 
            <!-- Page content wrapper-->
            <div id="page-content-wrapper">
            	<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
                    <div class="container-fluid">
                    	<input type="text" id="planName" name="planName" value="" placeholder="Enter Your Plan Name">
                        <button class="btn btn-primary" style="margin-left: 30%;" id="sidebarToggle">Toggle Menu</button>
                    </div>
                </nav>
                
            
                <!-- Summary content-->
                <div class="container-fluid" id="summary">
                    <h1 class="mt-4">Summary Content</h1>
                    <p>The starting state of the menu will appear collapsed on smaller screens, and will appear non-collapsed on larger screens. When toggled using the button below, the menu will change.</p>
                    <p>
                        Make sure to keep all page content within the
                        <code>#page-content-wrapper</code>
                        . The top navbar is optional, and just for demonstration. Just create an element with the
                        <code>#sidebarToggle</code>
                        ID which will toggle the menu when clicked.
                    </p>
                </div>
                
                
                <!-- Details content-->
                <div class="container-fluid" id="details" style="display: none;">
                    <h1 class="mt-4">Details Content</h1>
                    <p>The starting state of the menu will appear collapsed on smaller screens, and will appear non-collapsed on larger screens. When toggled using the button below, the menu will change.</p>
                    <p>
                        Make sure to keep all page content within the
                        <code>#page-content-wrapper</code>
                        . The top navbar is optional, and just for demonstration. Just create an element with the
                        <code>#sidebarToggle</code>
                        ID which will toggle the menu when clicked.
                    </p>
                </div>
                
                
                <!-- Map content-->
                <div class="container-fluid" id="map" style="display: none;">
                    <h1 class="mt-4">Map Content</h1>
                    <p>The starting state of the menu will appear collapsed on smaller screens, and will appear non-collapsed on larger screens. When toggled using the button below, the menu will change.</p>
                    <p>
                        Make sure to keep all page content within the
                        <code>#page-content-wrapper</code>
                        . The top navbar is optional, and just for demonstration. Just create an element with the
                        <code>#sidebarToggle</code>
                        ID which will toggle the menu when clicked.
                    </p>
                </div>
                
                
                
                
                
                
            </div>
        </div>
</div>
</section>



<%@ include file="footer.jsp"%>