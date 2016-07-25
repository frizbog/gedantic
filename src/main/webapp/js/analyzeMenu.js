//Default options
var options = {
		animationDuration: 0.5, // in seconds
		filter: 'all', // Initial filter
		callbacks: { 
			onFilteringStart: function() { },
			onFilteringEnd: function() { },
			onShufflingStart: function() { },
			onShufflingEnd: function() { },
			onSortingStart: function() { },
			onSortingEnd: function() { }
		},
		delay: 0, // Transition delay in ms
		delayMode: 'progressive', // 'progressive' or 'alternate'
		easing: 'ease-out',
		filterOutCss: { // Filtering out animation
			opacity: 0,
			transform: 'scale(0.5)'
		},
		filterInCss: { // Filtering in animation
			opacity: 0,
			transform: 'scale(1)'
		},
		layout: 'sameSize', // See layouts
		selector: '.filtr-container',
		setupControls: true 
} 
// You can override any of these options and then call...
var filterizd = $('.filtr-container').filterizr(options);
 