/*
	Copyright (c) 2004-2009, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dojox.date.buddhist.locale"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.date.buddhist.locale"] = true;
dojo.provide("dojox.date.buddhist.locale");
dojo.experimental("dojox.date.buddhist.locale");

dojo.require("dojox.date.buddhist.Date");
dojo.require("dojo.regexp");
dojo.require("dojo.string");
dojo.require("dojo.i18n");

dojo.requireLocalization("dojo.cldr", "buddhist", null, "ROOT,ar,be,da,de,el,eu,fr,gsw,hr,hu,ko,lt,nb,ne,no,pl,pt,pt-pt,ro,sh,sr,sr-latn,th");

(function(){
	// Format a pattern without literals
	function formatPattern(dateObject, bundle, locale, fullYear,  pattern){

		return pattern.replace(/([a-z])\1*/ig, function(match){
			var s, pad;
			var c = match.charAt(0);
			var l = match.length;
			var widthList = ["abbr", "wide", "narrow"];
			
			switch(c){
				case 'G':
					s = bundle["eraAbbr"][0];
					break;
				case 'y':
					s = String(dateObject.getFullYear());
					break;
				case 'M':
					var m = dateObject.getMonth();
					if(l<3){
						s = m+1; pad = true;
					}else{
						var propM = ["months", "format", widthList[l-3]].join("-");
						s = bundle[propM][m];
					}
					break;
				case 'd':
					s = dateObject.getDate(true); pad = true;
					break;
				case 'E':
					var d = dateObject.getDay();
					if(l<3){
						s = d+1; pad = true;
					}else{
						var propD = ["days", "format", widthList[l-3]].join("-");
						s = bundle[propD][d];
					}
					break;
				case 'a':
					var timePeriod = (dateObject.getHours() < 12) ? 'am' : 'pm';
					s = bundle[timePeriod];
					break;
				case 'h':
				case 'H':
				case 'K':
				case 'k':
					var h = dateObject.getHours();
					switch (c){
						case 'h': // 1-12
							s = (h % 12) || 12;
							break;
						case 'H': // 0-23
							s = h;
							break;
						case 'K': // 0-11
							s = (h % 12);
							break;
						case 'k': // 1-24
							s = h || 24;
							break;
					}
					pad = true;
					break;
				case 'm':
					s = dateObject.getMinutes(); pad = true;
					break;
				case 's':
					s = dateObject.getSeconds(); pad = true;
					break;
				case 'S':
					s = Math.round(dateObject.getMilliseconds() * Math.pow(10, l-3)); pad = true;
					break;
				default:
					throw new Error("dojox.date.buddhist.locale.formatPattern: invalid pattern char: "+pattern);
			}
			if(pad){ s = dojo.string.pad(s, l); }
			return s;
		});
	}	
	
dojox.date.buddhist.locale.format = function(/*buddhist.Date*/dateObject, /*object?*/options){
	// based on and similar to dojo.date.locale.format
	//summary:
	//		Format a Date object as a String, using  settings.
	options = options || {};

	var locale = dojo.i18n.normalizeLocale(options.locale);
	var formatLength = options.formatLength || 'short';
	var bundle = dojox.date.buddhist.locale._getBuddhistBundle(locale);
	var str = [];

	var sauce = dojo.hitch(this, formatPattern, dateObject, bundle, locale, options.fullYear);
	if(options.selector == "year"){
		var year = dateObject.getFullYear();
		return year;
	}
	if(options.selector != "time"){
		var datePattern = options.datePattern || bundle["dateFormat-"+formatLength];
		if(datePattern){str.push(_processPattern(datePattern, sauce));}
	}
	if(options.selector != "date"){
		var timePattern = options.timePattern || bundle["timeFormat-"+formatLength];
		if(timePattern){str.push(_processPattern(timePattern, sauce));}
	}
	var result = str.join(" "); //TODO: use locale-specific pattern to assemble date + time

	return result; // String
};	

dojox.date.buddhist.locale.regexp = function(/*object?*/options){
	//	based on and similar to dojo.date.locale.regexp	
	// summary:
	//		Builds the regular needed to parse a buddhist.Date
	return dojox.date.buddhist.locale._parseInfo(options).regexp; // String
};

dojox.date.buddhist.locale._parseInfo = function(/*oblect?*/options){
/* based on and similar to dojo.date.locale._parseInfo */

	options = options || {};
	var locale = dojo.i18n.normalizeLocale(options.locale);
	var bundle = dojox.date.buddhist.locale._getBuddhistBundle(locale);
	var formatLength = options.formatLength || 'short';
	var datePattern = options.datePattern || bundle["dateFormat-" + formatLength];
	var timePattern = options.timePattern || bundle["timeFormat-" + formatLength];

	var pattern;
	if(options.selector == 'date'){
		pattern = datePattern;
	}else if(options.selector == 'time'){
		pattern = timePattern;
	}else{
		pattern = (typeof (timePattern) == "undefined") ? datePattern : datePattern + ' ' + timePattern;
	}

	var tokens = [];
	
	var re = _processPattern(pattern, dojo.hitch(this, _buildDateTimeRE, tokens, bundle, options));
	return {regexp: re, tokens: tokens, bundle: bundle};
};




dojox.date.buddhist.locale.parse= function(/*String*/value, /*object?*/options){
	// based on and similar to dojo.date.locale.parse
	// summary: This function parse string date value according to options	
	value =  value.replace(/[\u200E\u200F\u202A-\u202E]/g, ""); //remove special chars
	
	if(!options){options={};}
	var info = dojox.date.buddhist.locale._parseInfo(options);
	
	var tokens = info.tokens, bundle = info.bundle;
	var re = new RegExp("^" + info.regexp + "$");
	
	var match = re.exec(value);

	var locale = dojo.i18n.normalizeLocale(options.locale); 

	if(!match){ 
		console.debug("dojox.date.buddhist.locale.parse: value  "+value+" doesn't match pattern   " + re);
		return null;
	} // null
	
	var date, date1;
	
	var result = [2513,0,1,0,0,0,0];  // buddhist date for [1970,0,1,0,0,0,0] used in gregorian locale
	var amPm = "";
	var mLength = 0;
	var widthList = ["abbr", "wide", "narrow"];
	var valid = dojo.every(match, function(v, i){
		if(!i){return true;}
		var token=tokens[i-1];
		var l=token.length;
		switch(token.charAt(0)){
			case 'y':
				result[0] = Number(v);
				break;
			case 'M':
				if(l>2){
					var months = bundle['months-format-' + widthList[l-3]].concat();
					if(!options.strict){
						//Tolerate abbreviating period in month part
						//Case-insensitive comparison
						v = v.replace(".","").toLowerCase();
						months = dojo.map(months, function(s){ return s ? s.replace(".","").toLowerCase() : s; } );
					}
					v = dojo.indexOf(months, v);
					if(v == -1){
						return false;
					}
					mLength = l;
				}else{
					v--;				
				}
				result[1] = Number(v);
				break;
			case 'D':
				result[1] = 0;
				// fallthrough...
			case 'd':
					result[2] =  Number(v);
				break;
			case 'a': //am/pm
				var am = options.am || bundle.am;
				var pm = options.pm || bundle.pm;
				if(!options.strict){
					var period = /\./g;
					v = v.replace(period,'').toLowerCase();
					am = am.replace(period,'').toLowerCase();
					pm = pm.replace(period,'').toLowerCase();
				}
				if(options.strict && v != am && v != pm){
					return false;
				}

				// we might not have seen the hours field yet, so store the state and apply hour change later
				amPm = (v == pm) ? 'p' : (v == am) ? 'a' : '';
				break;
			case 'K': //hour (1-24)
				if(v == 24){ v = 0; }
				// fallthrough...
			case 'h': //hour (1-12)
			case 'H': //hour (0-23)
			case 'k': //hour (0-11)
				//in the 12-hour case, adjusting for am/pm requires the 'a' part
				//which could come before or after the hour, so we will adjust later
				result[3] = Number(v);
				break;
			case 'm': //minutes
				result[4] = Number(v);
				break;
			case 's': //seconds
				result[5] = Number(v);
				break; 
			case 'S': //milliseconds
				result[6] = Number(v);
		}
		return true;
	});

	var hours = +result[3];
	if(amPm === 'p' && hours < 12){
		result[3] = hours + 12; //e.g., 3pm -> 15
	}else if(amPm === 'a' && hours == 12){
		result[3] = 0; //12am -> 0
	}
	var dateObject = new dojox.date.buddhist.Date(result[0], result[1], result[2], result[3], result[4], result[5], result[6]);
	return dateObject;
};


function _processPattern(pattern, applyPattern, applyLiteral, applyAll){
	//summary: Process a pattern with literals in it

	// Break up on single quotes, treat every other one as a literal, except '' which becomes '
	var identity = function(x){return x;};
	applyPattern = applyPattern || identity;
	applyLiteral = applyLiteral || identity;
	applyAll = applyAll || identity;

	//split on single quotes (which escape literals in date format strings) 
	//but preserve escaped single quotes (e.g., o''clock)
	var chunks = pattern.match(/(''|[^'])+/g); 
	var literal = pattern.charAt(0) == "'";

	dojo.forEach(chunks, function(chunk, i){
		if(!chunk){
			chunks[i]='';
		}else{
			chunks[i]=(literal ? applyLiteral : applyPattern)(chunk);
			literal = !literal;
		}
	});
	return applyAll(chunks.join(''));
}

function _buildDateTimeRE  (tokens, bundle, options, pattern){
		// based on and similar to dojo.date.locale._buildDateTimeRE 
		//
	
	pattern = dojo.regexp.escapeString(pattern); 
	var locale = dojo.i18n.normalizeLocale(options.locale);
	
	return pattern.replace(/([a-z])\1*/ig, function(match){

			// Build a simple regexp.  Avoid captures, which would ruin the tokens list
			var s;
			var c = match.charAt(0);
			var l = match.length;
			var p2 = '', p3 = '';
			if(options.strict){
				if(l > 1){ p2 = '0' + '{'+(l-1)+'}'; }
				if(l > 2){ p3 = '0' + '{'+(l-2)+'}'; }
			}else{
				p2 = '0?'; p3 = '0{0,2}';
			}
			switch(c){
				case 'y':
					s = '\\d+';
					break;
				case 'M':
					s = (l>2) ?  '\\S+' : p2+'[1-9]|1[0-2]';
					break;
				case 'd':
					s = '[12]\\d|'+p2+'[1-9]|3[01]';
					break;
				case 'E':
					s = '\\S+';
					break;
				case 'h': //hour (1-12)
					s = p2+'[1-9]|1[0-2]';
					break;
				case 'k': //hour (0-11)
					s = p2+'\\d|1[01]';
					break;
				case 'H': //hour (0-23)
					s = p2+'\\d|1\\d|2[0-3]';
					break;
				case 'K': //hour (1-24)
					s = p2+'[1-9]|1\\d|2[0-4]';
					break;
				case 'm':
				case 's':
					s = p2+'\\d|[0-5]\\d';
					break;
				case 'S':
					s = '\\d{'+l+'}';
					break;
				case 'a':
					var am = options.am || bundle.am || 'AM';
					var pm = options.pm || bundle.pm || 'PM';
					if(options.strict){
						s = am + '|' + pm;
					}else{
						s = am + '|' + pm;
						if(am != am.toLowerCase()){ s += '|' + am.toLowerCase(); }
						if(pm != pm.toLowerCase()){ s += '|' + pm.toLowerCase(); }
					}
					break;
				default:
					s = ".*";
			}	 
			if(tokens){ tokens.push(match); }
			return "(" + s + ")"; // add capture
		}).replace(/[\xa0 ]/g, "[\\s\\xa0]"); // normalize whitespace.  Need explicit handling of \xa0 for IE. */
}
})();



(function(){
var _customFormats = [];
dojox.date.buddhist.locale.addCustomFormats = function(/*String*/packageName, /*String*/bundleName){
	// summary:
	//		Add a reference to a bundle containing localized custom formats to be
	//		used by date/time formatting and parsing routines.
	_customFormats.push({pkg:packageName,name:bundleName});
};

dojox.date.buddhist.locale._getBuddhistBundle = function(/*String*/locale){
	var buddhist = {};
	dojo.forEach(_customFormats, function(desc){
		var bundle = dojo.i18n.getLocalization(desc.pkg, desc.name, locale);
		buddhist = dojo.mixin(buddhist, bundle);
	}, this);
	return buddhist; /*Object*/
};
})();

dojox.date.buddhist.locale.addCustomFormats("dojo.cldr","buddhist");

dojox.date.buddhist.locale.getNames = function(/*String*/item, /*String*/type, /*String?*/context, /*String?*/locale, /*buddhist Date Object?*/date){
	// summary:
	//		Used to get localized strings from dojo.cldr for day or month names.
	var label;
	var lookup = dojox.date.buddhist.locale._getBuddhistBundle;
	var props = [item, context, type];
	if(context == 'standAlone'){
		var key = props.join('-');
		label = lookup(locale)[key];
		// Fall back to 'format' flavor of name
		if(label === lookup("ROOT")[key]){ label = undefined; } // a bit of a kludge, in the absense of real aliasing support in dojo.cldr
	}
	props[1] = 'format';
	
	// return by copy so changes won't be made accidentally to the in-memory model
	return (label || lookup(locale)[props.join('-')]).concat(); /*Array*/
};

}
