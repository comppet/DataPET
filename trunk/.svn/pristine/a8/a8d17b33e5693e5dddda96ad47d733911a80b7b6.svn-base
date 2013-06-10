/* 
 * Este arquivo contém funções básicas de Javascript utilizadas no DataPET.
 */

function setVisible(id){
    document.getElementById(id).style.visibility = 'visible';
}

function setHidden(id){
    document.getElementById(id).style.visibility = 'hidden';
}

function toogleVisibility(id){
    component = document.getElementById(id);
    if (component.style.visibility == 'hidden')
        component.style.visibility = 'visible';
    else component.style.visibility = 'hidden';
}

function numericoAumentar(idCampo, max){
    campo = document.getElementById(idCampo);
    if (campo.value < max) ++campo.value;
    else alert('O valor n\xE3o pode ultrapassar ' + max);
}

function ajustarData(idDataInicial, idDataFinal ){
    dataInicial = document.getElementById(idDataInicial);
    dataFinal = document.getElementById(idDataFinal);
    dataFinal.value = dataInicial.value;
    alert('Chamou!');
    //if (campo.value < max) ++campo.value;
    //else alert('O valor n\xE3o pode ultrapassar ' + max);
}

function numericoDiminuir(idCampo, min){
    campo = document.getElementById(idCampo);
    if (campo.value > min) --campo.value;
    else alert('O valor n\xE3o pode ser menor que ' + min);
}

function setValue(idCampo, value){
    campo = document.getElementById(idCampo);
    campo.value = value;
}

function setText(idComponente, texto){
    comp = document.getElementById(idComponente);
    comp.innerHTML = texto;
}

function inverterGrupoCheckbox(checkbox, idGrupo, n){
    for (i = 0; i < n; i++){
        elemento = document.getElementById(idGrupo + ':' + i);
        elemento.checked = checkbox.checked;
    }
}

function limitarTextArea(myfield, limite, e)
{
    var key;

    if (window.event)
       key = window.event.keyCode;
    else if (e)
       key = e.which;
    else
       return true;

    // control keys
    if ((key==null) || (key==0) || (key==8) ||
        (key==9) || (key==13) || (key==27) )
       return true;

    else if (myfield.value.length < limite)
       return true;
    else
        return false;
}

// copyright 1999 Idocs, Inc. http://www.idocs.com
// Distribute this script freely but keep this notice in place
function numbersonly(myfield, e, dec)
{
    var key;
    var keychar;

    if (window.event)
       key = window.event.keyCode;
    else if (e)
       key = e.which;
    else
       return true;
    keychar = String.fromCharCode(key);

    // control keys
    if ((key==null) || (key==0) || (key==8) ||
        (key==9) || (key==13) || (key==27) )
       return true;

    // numbers
    else if ((("0123456789").indexOf(keychar) > -1))
       return true;

    // decimal point jump
    else if (dec && (keychar == "."))
       {
       myfield.form.elements[dec].focus();
       return false;
       }
    else
       return false;
}

function numbersnota(myfield, e, dec)
{
    
    var key;
    var keychar;

    if (window.event)
       key = window.event.keyCode;
    else if (e)
       key = e.which;
    else
       return true;
    keychar = String.fromCharCode(key);

    // control keys
    if ((key==null) || (key==0) || (key==8) ||
        (key==9) || (key==13) || (key==27) )
       return true;

    // numbers
    else if ((("0123456789.").indexOf(keychar) > -1)){
        partes = myfield.value.split('.');
        //alert(partes.length);
        if (keychar == '.'){
            if(partes.length == 1)
                return true;
            else return false;
        }
        else return true;

    }

    return false;
       

    // decimal point jump
    /*else if (dec && (keychar == "."))
       {
       myfield.form.elements[dec].focus();
       return false;
       }
    else
       return false;*/

}

function valorMaximo(field, max){
    if (field.value > max)
        field.value = max;
}

/*
function getSimpleId(id){
    var partes = id.split(':');
    var n_partes = partes.length;
    var id_simples = partes[n_partes - 1];
    return id_simples;
}

function getComplexId(id){
    var partes = id.split(':');
    var n_partes = partes.length;
    var id_complexo = '';
    for (i = 0; i < n_partes - 1; i++)
        id_complexo += partes[i] + ':';
    return id_complexo;
}
*/

