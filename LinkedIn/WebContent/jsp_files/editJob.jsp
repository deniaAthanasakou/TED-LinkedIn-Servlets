<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css_files/createJob.css" type="text/css">
		<title>Edit job</title>
		
		<script src="${pageContext.request.contextPath}/js_files/setSelected.js"></script>
	</head>
	<body>
	
		<jsp:include page="Header.jsp" /> 
		
		<div class="main">
			<div class="createJobInfo">
				<form method="post" action="${pageContext.request.contextPath}/JobHandle?action=editJob&jobId=${job.id.jobId}" accept-charset="utf-8">
					<div class="infoLabel">
						<h3>Κύρια Χαρακτηριστικά Αγγελίας</h3>
					</div>
					
					<div class="jobInfoSection">
						<div class="row">
							<div class="col-md-4">
								<div class="form-group">
								    <label class="sr-only" for="jobTitle">Τίτλος Αγγελίας</label>
								    <input type="text" class="form-control" id="jobTitle" name="jobTitle" placeholder="Τίτλος Αγγελίας" value="${job.title}" maxlength="60" required>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
								    <label class="sr-only" for="jobIndustry">Εταιρεία</label>
								    <input type="text" class="form-control" id="jobIndustry" name="jobIndustry" placeholder="Εταιρεία" value="${job.company}" maxlength="45" required>
								</div>
							</div>
							<div class="col-md-4">
								<div class="form-group">
								    <label class="sr-only" for="jobLocation">Τοποθεσία</label>
								    <input type="text" class="form-control" id="jobLocation" name="jobLocation" placeholder="Τοποθεσία" maxlength="200" value="${job.location}" required>
								</div>
							</div>
						</div>
					</div>
					
					<div class="infoLabel">
						<h3>Επιπλέον Χαρακτηριστικά</h3>
					</div>
					
					<div class="jobInfoSection">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<c:if test="${requestScope.jobFunctionError != null }">
										<div class="alert alert-danger">
  											<strong>Σφάλμα!</strong> ${requestScope.jobFunctionError}
										</div>
									</c:if>
								    <label for="jobFunction">Λειτουργίες εργασίας(Επιλέξτε μέχρι 3)*</label>
								    <select multiple class="form-control" id="jobFunction" name="jobFunction" required>
								    	<option value="">Παρακαλώ επιλέξτε...</option>
									    <option value="Λογιστική">Λογιστική</option>
									    <option value="Διοικητικό">Διοικητικό</option>
									    <option value="Τέχνες και Σχεδιασμός">Τέχνες και Σχεδιασμός</option>
									    <option value="Ανάπτυξη Επιχειρήσεων">Ανάπτυξη Επιχειρήσεων</option>
									    <option value="Κοινωνικές και κοινωνικές υπηρεσίες">Κοινωνικές και κοινωνικές υπηρεσίες</option>
									    <option value="Συμβουλευτική">Συμβουλευτική</option>
									    <option value="Εκπαίδευση">Εκπαίδευση</option>
									    <option value="Μηχανική">Μηχανική</option>
									    <option value="Επιχειρηματικότητα">Επιχειρηματικότητα</option>
									    <option value="Χρηματοδοτική">Χρηματοδοτική</option>
									    <option value="Υπηρεσίες Υγείας">Υπηρεσίες Υγείας</option>
									    <option value="Ανθρώπινο Δυναμικό">Ανθρώπινο Δυναμικό</option>
									    <option value="Πληροφορική">Πληροφορική</option>
									    <option value="Νομική">Νομική</option>
									    <option value="Μάρκετινγκ">Μάρκετινγκ</option>
									    <option value="Μέσα & Επικοινωνίες">Μέσα & Επικοινωνίες</option>
									    <option value="Στρατιωτικές και Προστατευτικές Υπηρεσίες">Στρατιωτικές και Προστατευτικές Υπηρεσίες</option>
									    <option value="Λειτουργίες">Λειτουργίες</option>
									    <option value="Διαχείριση προϊόντων">Διαχείριση προϊόντων</option>
									    <option value="Προγράμματα & Διαχείριση Προϊόντων">Προγράμματα & Διαχείριση Προϊόντων</option>
									    <option value="Αγορών">Αγορών</option>
									    <option value="Διασφάλιση ποιότητας">Διασφάλιση ποιότητας</option>
									    <option value="Ακίνητα">Ακίνητα</option>
									    <option value="Έρευνα">Έρευνα</option>
									    <option value="Πωλήσεις">Πωλήσεις</option>
									    <option value="Υποστήριξη">Υποστήριξη</option>
							    	</select>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
								    <label for="employmentType">Τύπος εργασίας*</label>
								    <select class="form-control" id="employmentType" name="employmentType" required>
								    	<option value="">Παρακαλώ επιλέξτε...</option>
									    <option value="Πλήρης Απασχόληση">Πλήρης Απασχόληση</option>
									    <option value="Μερική Απασχόληση">Μερική Απασχόληση</option>
									    <option value="Με σύμβαση">Με σύμβαση</option>
									    <option value="Προσωρινή">Προσωρινή</option>
									    <option value="Εθελοντική">Εθελοντική</option>
									    <option value="Πρακτική">Πρακτική</option>
							    	</select>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<c:if test="${requestScope.companyTypesError != null }">
										<div class="alert alert-danger">
  											<strong>Σφάλμα!</strong> ${requestScope.companyTypesError}
										</div>
									</c:if>
								    <label for="companyIndustry">Είδος εταιρείας(Επιλέξτε μέχρι 3)*</label>
								    <select multiple class="form-control" id="companyIndustry" name="companyIndustry" required>
								    	<option value="">Παρακαλώ επιλέξτε...</option>
								    	<option value="Λογιστική">Λογιστική</option>
										<option value="Αεροπορικές εταιρείες / Αεροπορία">Αεροπορικές εταιρείες / Αεροπορία</option>
										<option value="Εναλλακτική επίλυση διαφορών">Εναλλακτική επίλυση διαφορών</option>
										<option value="ναλλακτικό φάρμακο">Εναλλακτικό φάρμακο</option>
										<option value="Κινουμένων σχεδίων">Κινουμένων σχεδίων</option>
										<option value="Ένδυση & Μόδα">Ένδυση & Μόδα</option>
										<option value="Αρχιτεκτονική & Σχεδιασμός">Αρχιτεκτονική & Σχεδιασμός</option>
										<option value="Τέχνες και χειροτεχνίες">Τέχνες και χειροτεχνίες</option>
										<option value="Αυτοκίνητο">Αυτοκίνητο</option>
										<option value="Αεροπορία & Αεροδιαστημική">Αεροπορία & Αεροδιαστημική</option>
										<option value="Τραπεζικές εργασίες">Τραπεζικές εργασίες</option>
										<option value="Βιοτεχνολογία">Βιοτεχνολογία</option>
										<option value="Μέσα εκπομπής">Μέσα εκπομπής</option>
										<option value="Οικοδομικά υλικά">Οικοδομικά υλικά</option>
										<option value="Επιχειρησιακά αναλώσιμα και εξοπλισμός">Επιχειρησιακά αναλώσιμα και εξοπλισμός</option>
										<option value="Κεφαλαιαγορές">Κεφαλαιαγορές</option>
										<option value="Χημικές ουσίες">Χημικές ουσίες</option>
										<option value="Κοινωνική και Κοινωνική Οργάνωση">Κοινωνική και Κοινωνική Οργάνωση</option>
										<option value="Πολιτική Μηχανική">Πολιτική Μηχανική</option>
										<option value="Εμπορική ακίνητη περιουσία">Εμπορική ακίνητη περιουσία</option>
										<option value="Ασφάλεια υπολογιστών και δικτύων">Ασφάλεια υπολογιστών και δικτύων</option>
										<option value="Παιχνίδια υπολογιστών">Παιχνίδια υπολογιστών</option>
										<option value="Υλικό υπολογιστή">Υλικό υπολογιστή</option>
										<option value="Δικτύωση υπολογιστών">Δικτύωση υπολογιστών</option>
										<option value="Λογισμικό Ηλεκτρονικών Υπολογιστών">Λογισμικό Ηλεκτρονικών Υπολογιστών</option>
										<option value="Κατασκευή">Κατασκευή</option>
										<option value="Καταναλωτική Ηλεκτρονική">Καταναλωτική Ηλεκτρονική</option>
										<option value="Καταναλωτικά αγαθά">Καταναλωτικά αγαθά</option>
										<option value="Υπηρεσίες καταναλωτών">Υπηρεσίες καταναλωτών</option>
										<option value="Καλλυντικά">Καλλυντικά</option>
										<option value="Γαλακτοκομικά">Γαλακτοκομικά</option>
										<option value="Άμυνα & Διάστημα">Άμυνα & Διάστημα</option>
										<option value="Σχέδιο">Σχέδιο</option>
										<option value="Διαχείριση εκπαίδευσης">Διαχείριση εκπαίδευσης</option>
										<option value="Ηλεκτρονική μάθηση">Ηλεκτρονική μάθηση</option>
										<option value="Ηλεκτρολογικής και Ηλεκτρονικής Βιομηχανίας">Ηλεκτρολογικής και Ηλεκτρονικής Βιομηχανίας</option>
										<option value="Ψυχαγωγία">Ψυχαγωγία</option>
										<option value="Περιβαλλοντικές υπηρεσίες">Περιβαλλοντικές υπηρεσίες</option>
										<option value="Υπηρεσίες εκδηλώσεων">Υπηρεσίες εκδηλώσεων</option>
										<option value="Εκτελεστικό Γραφείο">Εκτελεστικό Γραφείο</option>
										<option value="Εγκαταστάσεις Υπηρεσίες">Εγκαταστάσεις Υπηρεσίες</option>
										<option value="Καλλιέργεια">Καλλιέργεια</option>
										<option value="Χρηματοπιστωτικές υπηρεσίες">Χρηματοπιστωτικές υπηρεσίες</option>
										<option value="Καλές τέχνες">Καλές τέχνες</option>
										<option value="Αλιεία">Αλιεία</option>
										<option value="Τρόφιμα & Ποτά">Τρόφιμα & Ποτά</option>
										<option value="Παραγωγή φαγητού">Παραγωγή φαγητού</option>
										<option value="Έρανος">Έρανος</option>
										<option value="Επιπλα">Επιπλα</option>
										<option value="Τυχερά παιχνίδια και καζίνο">Τυχερά παιχνίδια και καζίνο</option>
										<option value="Γυαλί, Κεραμικά & Σκυρόδεμα">Γυαλί, Κεραμικά & Σκυρόδεμα</option>
										<option value="Κυβέρνηση">Κυβέρνηση</option>
										<option value="Κυβερνητικές Σχέσεις">Κυβερνητικές Σχέσεις</option>
										<option value="Γραφικό σχέδιο">Γραφικό σχέδιο</option>
										<option value="Υγεία, Wellness & Fitness">Υγεία, Wellness & Fitness</option>
										<option value="Ανώτερη εκπαίδευση">Ανώτερη εκπαίδευση</option>
										<option value="Νοσοκομειακή φροντίδα">Νοσοκομειακή φροντίδα</option>
										<option value="Φιλοξενία">Φιλοξενία</option>
										<option value="Ανθρώπινο δυναμικό">Ανθρώπινο δυναμικό</option>
										<option value="Εισαγωγή-εξαγωγή">Εισαγωγή-εξαγωγή</option>
										<option value="Ατομικές και οικογενειακές υπηρεσίες">Ατομικές και οικογενειακές υπηρεσίες</option>
										<option value="Βιομηχανικός αυτοματισμός">Βιομηχανικός αυτοματισμός</option>
										<option value="Υπηρεσίες πληροφοριών">Υπηρεσίες πληροφοριών</option>
										<option value="Πληροφορική & Υπηρεσίες">Πληροφορική & Υπηρεσίες</option>
										<option value="Ασφάλιση">Ασφάλιση</option>
										<option value="Διεθνείς σχέσεις">Διεθνείς σχέσεις</option>
										<option value="Διεθνές εμπόριο και ανάπτυξη">Διεθνές εμπόριο και ανάπτυξη</option>
										<option value="Διαδίκτυο">Διαδίκτυο</option>
										<option value="Επενδυτική Τραπεζική / Venture">Επενδυτική Τραπεζική / Venture</option>
										<option value="Διαχείριση επενδύσεων">Διαχείριση επενδύσεων</option>
										<option value="Δικαστήρια">Δικαστήρια</option>
										<option value="Επιβολή του νόμου">Επιβολή του νόμου</option>
										<option value="Νομική Πρακτική">Νομική Πρακτική</option>
										<option value="Νομικές υπηρεσίες">Νομικές υπηρεσίες</option>
										<option value="Νομοθετικό γραφείο">Νομοθετικό γραφείο</option>
										<option value="Ταξίδι αναψυχής">Ταξίδι αναψυχής</option>
										<option value="Βιβλιοθήκες">Βιβλιοθήκες</option>
										<option value="Εφοδιαστική αλυσίδα και αλυσίδα εφοδιασμού">Εφοδιαστική αλυσίδα και αλυσίδα εφοδιασμού</option>
										<option value="Πολυτελή Είδη & Κοσμήματα">Πολυτελή Είδη & Κοσμήματα</option>
										<option value="Μηχανήματα">Μηχανήματα</option>
										<option value="Συμβουλευτικές υπηρεσίες διαχείρισης">Συμβουλευτικές υπηρεσίες διαχείρισης</option>
										<option value="Θαλάσσιος">Θαλάσσιος</option>
										<option value="Μάρκετινγκ & Διαφήμιση">Μάρκετινγκ & Διαφήμιση</option>
										<option value="Έρευνα αγοράς">Έρευνα αγοράς</option>
										<option value="Μηχανική ή Βιομηχανική Μηχανική">Μηχανική ή Βιομηχανική Μηχανική</option>
										<option value=">Παραγωγή πολυμέσων">Παραγωγή πολυμέσων</option>
										<option value="Ιατρική συσκευή">Ιατρική συσκευή</option>
										<option value="Ιατρική πρακτική">Ιατρική πρακτική</option>
										<option value="Ψυχική Υγεία">Ψυχική Υγεία</option>
										<option value="Στρατός">Στρατός</option>
										<option value="Μεταλλεία & Μέταλλα">Μεταλλεία & Μέταλλα</option>
										<option value="Κινούμενες εικόνες και ταινίες">Κινούμενες εικόνες και ταινίες</option>
										<option value="Μουσεία & Ιδρύματα">Μουσεία & Ιδρύματα</option>
										<option value="Μουσική">Μουσική</option>
										<option value="Νανοτεχνολογία">Νανοτεχνολογία</option>
										<option value="Εφημερίδες">Εφημερίδες</option>
										<option value="Μη κερδοσκοπική οργάνωση">Μη κερδοσκοπική οργάνωση</option>
										<option value="Πετρέλαιο & Ενέργεια">Πετρέλαιο & Ενέργεια</option>
										<option value="Ηλεκτρονική δημοσίευση">Ηλεκτρονική δημοσίευση</option>
										<option value="Outsourcing / Offshoring">Outsourcing / Offshoring</option>
										<option value="Πακέτο / Παράδοση εμπορευμάτων">Πακέτο / Παράδοση εμπορευμάτων</option>
										<option value="Συσκευασία & εμπορευματοκιβώτια">Συσκευασία & εμπορευματοκιβώτια</option>
										<option value="Χαρτικά και δασικά προϊόντα">Χαρτικά και δασικά προϊόντα</option>
										<option value="Τέχνες του θεάματος">Τέχνες του θεάματος</option>
										<option value="Φαρμακευτικά προϊόντα">Φαρμακευτικά προϊόντα</option>
										<option value="Φιλανθρωπία">Φιλανθρωπία</option>
										<option value="Φωτογραφία">Φωτογραφία</option>
										<option value="Πλαστικά είδη">Πλαστικά είδη</option>
										<option value="Πολιτικός Οργανισμός">Πολιτικός Οργανισμός</option>
										<option value="Πρωτοβάθμια / Δευτεροβάθμια Εκπαίδευση">Πρωτοβάθμια / Δευτεροβάθμια Εκπαίδευση</option>
										<option value="Εκτύπωση">Εκτύπωση</option>
										<option value="Επαγγελματική εκπαίδευση">Επαγγελματική εκπαίδευση</option>
										<option value="Ανάπτυξη προγράμματος">Ανάπτυξη προγράμματος</option>
										<option value="Δημόσια πολιτική">Δημόσια πολιτική</option>
										<option value="Δημόσιες σχέσεις">Δημόσιες σχέσεις</option>
										<option value="Δημόσια ασφάλεια">Δημόσια ασφάλεια</option>
										<option value="Εκδόσεις">Εκδόσεις</option>
										<option value="Κατασκευή σιδηροδρόμων">Κατασκευή σιδηροδρόμων</option>
										<option value="Εκτροφή">Εκτροφή</option>
										<option value="Ακίνητα">Ακίνητα</option>
										<option value="Ψυχαγωγία">Ψυχαγωγία</option>
										<option value="Εγκαταστάσεις & Υπηρεσίες">Εγκαταστάσεις & Υπηρεσίες</option>
										<option value="Θρησκευτικά Ιδρύματα">Θρησκευτικά Ιδρύματα</option>
										<option value="Ανανεώσιμες Πηγές Ενέργειας & Περιβάλλον">Ανανεώσιμες Πηγές Ενέργειας & Περιβάλλον</option>
										<option value="Έρευνα">Έρευνα</option>
										<option value="Εστιατόρια">Εστιατόρια</option>
										<option value="Λιανεμπόριο">Λιανεμπόριο</option>
										<option value="Ασφάλεια και έρευνες">Ασφάλεια και έρευνες</option>
										<option value="Ημιαγωγοί">Ημιαγωγοί</option>
										<option value="Ναυπηγική">Ναυπηγική</option>
										<option value="Αθλητικά είδη">Αθλητικά είδη</option>
										<option value="Αθλητισμός">Αθλητισμός</option>
										<option value="Στελέχωση και πρόσληψη">Στελέχωση και πρόσληψη</option>
										<option value="Σούπερ μάρκετ">Σούπερ μάρκετ</option>
										<option value="Τηλεπικοινωνίες">Τηλεπικοινωνίες</option>
										<option value="Υφάσματα">Υφάσματα</option>
										<option value="Δεξαμενές σκέψης">Δεξαμενές σκέψης</option>
										<option value="Καπνός">Καπνός</option>
										<option value="Μετάφραση & Εντοπισμός">Μετάφραση & Εντοπισμός</option>
										<option value="Μεταφορές / Σιδηρόδρομος">Μεταφορές / Σιδηρόδρομος</option>
										<option value="Βοηθητικά προγράμματα">Βοηθητικά προγράμματα</option>
										<option value="Επιχειρηματικών κεφαλαίων">Επιχειρηματικών κεφαλαίων</option>
										<option value="Κτηνιατρεία">Κτηνιατρεία</option>
										<option value="Αποθήκευση">Αποθήκευση</option>
										<option value="Χονδρικό εμπόριο">Χονδρικό εμπόριο</option>
										<option value="Κρασί και οινοπνευματώδη ποτά">Κρασί και οινοπνευματώδη ποτά</option>
										<option value="Ασύρματος">Ασύρματος</option>
							    	</select>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
								    <label for="seniorityLevel">Είδος εμπειρίας*</label>
								    <select class="form-control" id="seniorityLevel" name="seniorityLevel" required>
								    	<option value="">Παρακαλώ επιλέξτε...</option>
									    <option value="Πρακτική">Πρακτική</option>
									    <option value="Αρχάριο Επίπεδο">Αρχάριο Επίπεδο</option>
									    <option value="Συνέταιρος">Συνέταιρος</option>
									    <option value="Μεσαία-Ανώτερη Βαθμίδα">Μεσαία-Ανώτερη Βαθμίδα</option>
									    <option value="Διεθυντής">Διεθυντής</option>
									    <option value="Εκτελεστικός">Εκτελεστικός</option>
							    	</select>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="jobDescription">Περιγραφή εργασίας*</label>
									<textarea class="form-control" rows="3" name="jobDescription" id="jobDescription" maxlength="10000" required>${job.description}</textarea>
								</div>
							</div>
						</div>
					</div>
					
					<div class="infoLabel">
						<h3>Δεξιότητες και Εμπειρία</h3>
					</div>
					
					<div class="jobInfoSection">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<c:if test="${requestScope.skillsError != null }">
										<div class="alert alert-danger">
  											<strong>Σφάλμα!</strong> ${requestScope.skillsError}
										</div>
									</c:if>
								    <label for="skills">Δεξιότητες(Χωρίστε τις δεξιότητες με κόμμα, επιλέξτε μέχρι 10)*</label>
								    <textarea class="form-control" rows="2" name="skills" id="skills" placeholder="Δεξιότητες..." maxlength="1000" required>${job.skills}</textarea>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<c:if test="${requestScope.experienceFromToError != null }">
									<div class="alert alert-danger">
										<strong>Σφάλμα!</strong> ${requestScope.experienceFromToError}
									</div>
								</c:if>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">	
									<label for="fromYears">Εμπειρία από έτη*</label>
									<input type="number" class="form-control" id="fromYears" name="fromYears" value="${job.experienceFrom}" min="0" max="50" required>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">	
									<label for="toYears">Μέχρι*</label>
									<input type="number" class="form-control" id="toYears" name="toYears" value="${job.experienceTo}" min="0" max="50" required>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<c:if test="${requestScope.educationLevelError != null }">
										<div class="alert alert-danger">
  											<strong>Σφάλμα!</strong> ${requestScope.educationLevelError}
										</div>
									</c:if>
								    <label for="educationLevel">Επίπεδο εκπαίδευσης(Επιλέξτε μέχρι 5)*</label>
								    <select multiple class="form-control" id="educationLevel" name="educationLevel" required>
								    	<option value="">Παρακαλώ επιλέξτε...</option>
									    <option value="Απολυτήριο Λυκείου">Απολυτήριο Λυκείου</option>
									    <option value="Πτυχίο Συνεργάτη">Πτυχίο Συνεργάτη</option>
									    <option value="Μεταπτυχιακό">Μεταπτυχιακό</option>
									    <option value="Μεταπτυχιακό στην Διοίκηση Επιχειρήσεων">Μεταπτυχιακό στην Διοίκηση Επιχειρήσεων</option>
									    <option value="Δόκτορας της Φιλοσοφίας">Δόκτορας της Φιλοσοφίας</option>
									    <option value="Δόκτορας">Δόκτορας</option>
									    <option value=">Δόκτορας του νόμου">Δόκτορας του νόμου</option>
									    <option value="Πτυχίο">Πτυχίο</option>
							    	</select>
								</div>
							</div>
						</div>
					</div>
					
					<div class="infoLabel">
						<h3>Χρηματικό Ποσό</h3>
					</div>
					
					<div class="jobInfoSection">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="dailyMoney">Ημερήσια πληρωμή σε EUR*</label>
									<input type="number" step=0.01 class="form-control" id="dailyMoney" name="dailyMoney" value="${job.dailySalary}" min="0" required>
								</div>
							</div>
						</div>
					</div>
					
					<div class="pull-left" style="margin-left:10px;">
						<button type="button" class="btn btn-danger" onclick="window.location.href='${pageContext.request.contextPath}/jsp_files/jobs.jsp'"><i class="glyphicon glyphicon-remove"></i> Ακύρωση</button>
						<button type="reset" class="btn btn-warning"><i class="glyphicon glyphicon-trash"></i> Καθαρισμός</button>
					</div>
					<button type="submit" class="btn btn-primary" style="float: right; margin-right:10px;"><i class="glyphicon glyphicon-ok"></i> Ολοκλήρωση Επεξεργασίας</button>
				</form>
			</div>
		</div>
		
		<jsp:include page="Footer.jsp"/>
		
		<script type='text/javascript'>
			setSelectedEducationLevel("${job.educationLevel}");
			setSelectedJobFunctions("${job.jobFunction}");
			setSelectedJobCompanyTypes("${job.jobCompanyType}");
			setSelectedJobType("${job.jobType}");
			setSelectedExperience("${job.experience}");
		</script>
	</body>
</html>