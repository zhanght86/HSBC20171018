window.nameIconGenerator = {};
window.nameIconGenerator.generateIcon = undefined;
window.nameIconGenerator.canvasElement = null;

function vec3(x, y, z) {
    this.x = x;
    this.y = y;
    this.z = z;
}

function NigColor(r, g, b, a) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;

    this.toString = function () {


        var result = 'rgba(' + this.r + ", " + this.g + ", " + this.b + ", " + (this.a / 255.0) + ")";
        return result;
    }
}

function NigHsl() {
    this.h = 0;
    this.s = 0;
    this.l = 0;

    function hueToRgb(h, h1, c, x) {
        if (h >= 360 || h < 0)
            return new vec3(0, 0, 0);
        if ((h1 >= 0) && (h1 < 1))
            return new vec3(c, x, 0);
        else if ((h1 >= 1) && (h1 < 2))
            return new vec3(x, c, 0);
        else if ((h1 >= 2) && (h1 < 3))
            return new vec3(0, c, x);
        else if ((h1 >= 3) && (h1 < 4))
            return new vec3(0, x, c);
        else if ((h1 >= 4) && (h1 < 5))
            return new vec3(x, 0, c);
        else if ((h1 >= 5) && (h1 < 6))
            return new vec3(c, 0, x);
        else
            return new vec3(0, 0, 0);
    }
    this.toRgba = function (alpha) {
        var h = (this.h / 239.0) * 360;
        var s = this.s / 240.0;
        var l = this.l / 240.0;

        var c = (1 - Math.abs(2 * l - 1)) * s;
        var h1 = h / 60;
        var x = c * (1 - Math.abs(((h1) % 2) - 1));
        var rgb = hueToRgb(h, h1, c, x);
        var m = l - 0.5 * c;
        return new NigColor(Math.round((rgb.x + m) * 255),
        		Math.round((rgb.y + m) * 255),
        				Math.round((rgb.z + m) * 255),
        		alpha);
    }
}

try{
    window.nameIconGenerator.canvasElement = document.createElement("CANVAS");
    if (window.nameIconGenerator.canvasElement) {
        window.nameIconGenerator.canvasElement.width = 626;
        window.nameIconGenerator.canvasElement.height = 626;
    }
}catch(exception){

}
window.nameIconGenerator.isCanvasSupported = function () {
    if (!window.nameIconGenerator)
        return false;
    if (!window.nameIconGenerator.canvasElement)
        return false;
    if (!window.nameIconGenerator.canvasElement.getContext)
        return false;
    return true;
}
if (window.nameIconGenerator.isCanvasSupported()) {
    window.nameIconGenerator.drawTextL2R = function (ctx, text) {
        ctx.font = "600px \'Microsoft Jhenghei\',\'Heiti TC\',SimSun-ExtB,sans-serif";
        var textWidth = ctx.measureText(text.toUpperCase());
        var x = this.canvasElement.width - textWidth.width;
        x /= 2;
        ctx.fillText(text.toUpperCase(),x, this.canvasElement.height / 2 + 225, 600);
    }
    window.nameIconGenerator.drawTextT2B = function (ctx, text, x) {
        if (text === undefined || text === null || !text.length)
            return;
        var fontHeight = 600 / text.length;
        ctx.font = fontHeight + "px \'Microsoft Jhenghei\',\'Heiti TC\',SimSun-ExtB,sans-serif";
        var y = fontHeight * 0.9;
        for (var i = 0; i < text.length; i++) {
            ctx.fillText(text[i], x, y, 300);
            y += fontHeight;
        }
    }
    window.nameIconGenerator.containsNonCjk = function(text){
        if (text === undefined || text === null || !text.length)
            return false;
        for (var i = 0; i < text.length; i++) {
            if (text[i] < "★")
                return true;
        }
        return false;

    }
    window.nameIconGenerator.drawName = function (ctx, text) {
        if (text === undefined || text === null || !text.length)
            return;
        if (text.length == 1 || text.length > 4 || this.containsNonCjk(text)) {
            this.drawTextL2R(ctx, text[0]);
        } else {
            var part1 = "";
            var part2 = "";
            for (var i = 0; i < text.length; i++) {
                if (i <= Math.floor(text.length / 2) - 1)
                    part1 += text[i];
                else
                    part2 += text[i];
            }
            this.drawTextT2B(ctx, part2, 10);
            this.drawTextT2B(ctx, part1, 305);
        }
    }
    window.nameIconGenerator.drawRoundRectangle = function (ctx, x, y, width, height, radius, fill, stroke) {
        ctx = this.canvasElement.getContext("2d");
        if (radius === null || radius === undefined || isNaN(radius)) {
            radius = 10;
        }
        if (!stroke && !fill) {
            fill = true;
        }
        ctx.moveTo(x + radius, y);
        ctx.lineTo(x + width - radius, y);
        ctx.quadraticCurveTo(x + width, y,y + width, y + radius);
        ctx.lineTo(x + width, y + height - radius);
        ctx.quadraticCurveTo(x + width, y + height, x + width - radius, y + height);
        ctx.lineTo(x + radius, y + height);
        ctx.quadraticCurveTo(x, y + height, x, y + height - radius);
        ctx.lineTo(x, y + radius);
        ctx.quadraticCurveTo(x, y, x + radius, y);
        if (fill) {
            ctx.fill();
        }
        if (stroke) {
            ctx.stroke();
        }
    }
    window.nameIconGenerator.computeHue = function (text) {
        if (text === undefined || text === null || !text.length)
            return 12;
        var result = 0;
        if (this.containsNonCjk(text) && text[0] <= 'Z') {
            var h = text.charCodeAt(0) - "A".charCodeAt(0);
            if (h < 0)
                h = 0 - h;
            h %= 26;
            result = 239 * ((26 - h) / 26);
        }
        for (var i = 0; i < text.length; i++) {
            var prod = text.charCodeAt(i);
            if (prod < 0)
                prod = 0 - prod;
            while (prod >= 239) {
                prod /= 239;
                result += prod;
                if (result < 0)
                    result = 0 - result;
                result %= 239;
            }
        }
        for (var i = 0; i < text.length; i++) {
            var prod = text.charCodeAt(i);
            if (prod < 0)
                prod = 0 - prod;
            while (prod >= 10) {
                var tmp = prod;
                prod /= 10;
                tmp = tmp - prod * 10;
                result += tmp * 64;
                if (result < 0)
                    result = 0 - result;
                result %= 239;
            }
        }
        if (result < 0)
            result = 0 - result;
        result %= 239;
        return result;
    }
    window.nameIconGenerator.isMs = function () {
        var isIe = (navigator.userAgent.indexOf(".NET") >= 0);
        var isEdge = (navigator.userAgent.indexOf("Edge") >= 0);
        return isIe || isEdge;
    }
    window.nameIconGenerator.toDataUrlForMs = function () {
        var ieCanvas = document.createElement("CANVAS");
        ieCanvas.width = 64;
        ieCanvas.height =64;
        var img = new Image(626, 626);
        img.src = this.canvasElement.toDataURL("image/png");
        var ieCtx = ieCanvas.getContext("2d");
        ieCtx.beginPath();
        ieCtx.drawImage(img, 0, 0, 64,64);
        ieCtx.closePath();
        return ieCanvas.toDataURL("image/png");
    }
    window.nameIconGenerator.generateIcon = function (nameStr) {
        var ctx = window.nameIconGenerator.canvasElement.getContext("2d");
        ctx.clearRect(0, 0, 626, 626);
        ctx.beginPath();
        ctx.strokeStyle = "#000000";
        var fillHsl = new NigHsl();
        fillHsl.s = 239;//171;
        fillHsl.l = 213;
        fillHsl.h = this.computeHue(nameStr);
        ctx.fillStyle = fillHsl.toRgba(255).toString();
        this.drawRoundRectangle(ctx, 0, 0, 626, 626, 100, true, false);
        var reversedColor = fillHsl.toRgba(255);
        reversedColor.r = 255 - reversedColor.r;

        reversedColor.g = 255 - reversedColor.g;

        reversedColor.b = 255 - reversedColor.b;
        ctx.fillStyle = reversedColor.toString();
        this.drawName(ctx, nameStr);
        ctx.closePath();
        if (!this.isMs())
            return this.canvasElement.toDataURL("image/png");
        else
            return this.toDataUrlForMs();
    }
}
