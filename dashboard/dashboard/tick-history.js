
$(document).ready(function () {

    displayPricingTickData();

	setInterval(displayPricingTickData, 8 * 1000);

});

function displayPricingTickData() {

	console.debug(" -> :: displayPricingTickData()");	

	$.ajax({

		type: "GET",
		
		url: traderExchangeURL + "/candlestick/CALLISTO",

		contentType: "text/plain",
		
		crossDomain: true,				

		success: function (responseData, status, jqXHR) {

	        console.log("responseData / ", responseData);

            var trHTML = '';

            for (var i = responseData.pricingHistory.length - 1; i >= 0; i--) {
	        
                trHTML += '<tr>';

                trHTML += '<td class="">' + responseData.pricingHistory[i].id + '</td>';

                trHTML += '<td class="">' + responseData.pricingHistory[i].ticker + '</td>';

                trHTML += '<td class="">' + responseData.pricingHistory[i].bid + '</td>';	

                trHTML += '<td class="">' + responseData.pricingHistory[i].ask + '</td>';

                trHTML += '<td class="">' + responseData.pricingHistory[i].priceChangeAmount + '</td>';
      trHTML += '<td class="">' + responseData.pricingHistory[i].priceChangePercentage + '</td>';

            }

            $('#activity-display-table  > tbody').html(trHTML); 

		},

		error: function (jqXHR, status) {

			console.log("Something Went wrong");

			console.log("exception");
			
			console.log(jqXHR);

			console.log("status");
			
			console.log(status);

		}

	});

}
