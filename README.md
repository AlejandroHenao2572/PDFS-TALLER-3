# PDFS-TALLER-3

- David Alejandro Patacon Henao

# Caso de Uso: Modulo de Analisis de Inversiones - ECIBank
ECIBank requiere el diseno de un modulo central para analizar el comportamiento de inversiones financieras de forma confiable, predecible y segura.   


## Ejercicio 1 - Ganancia Total del Portafolio
### Contexto: 
ECIBank necesita conocer cuanto dinero gano o perdio un portafolio completo de inversiones durante un periodo especifico.
### Objetivo: 
Calcular la ganancia monetaria total generada por todas las inversiones del portafolio, considerando tanto el capital invertido como su rendimiento.
### Resultado: 
Un unico valor monetario que represente la ganancia o perdida total del portafolio.
### Tecnologias Utilizadas:
Tail Recursion con notacion x::xs y pattern matching.

## Ejercicio 2 - Validacion de Inversiones
### Contexto:
Antes de incluir una inversion en los reportes oficiales, el banco debe verificar que el monto invertido sea valido, el rendimiento este dentro de rangos aceptables y el nivel de riesgo sea reconocido por la entidad.
### Objetivo:
Disenar un proceso que determine si una inversion puede ser analizada o si debe ser rechazada, indicando la razon.
### Resultado Esperado:
Una inversion aprobada para analisis o una explicacion clara del motivo de rechazo.
### Tecnologias Utilizadas:
Monadas Either, for-comprehension y manejo explicito de errores.

## Ejercicio 3 - Analisis de Tendencias de Ganancia
### Contexto:
Los analistas financieros quieren identificar como evoluciona la ganancia real de un producto a lo largo del tiempo para detectar comportamientos positivos o senales de alerta.
### Objetivo:
Analizar la evolucion temporal de una inversion comparando periodos consecutivos y enfocandose en rangos especificos de tiempo.
### Resultado Esperado:
Una estructura que permita identificar incrementos, disminuciones y cambios bruscos en la ganancia generada.
### Tecnologias Utilizadas:
Transformacion de colecciones con sliding, slice, zip/unzip y collect.

## Ejercicio 4 - Indicadores Financieros
### Contexto:
Para reportes ejecutivos, ECIBank necesita resumir el desempeno financiero de un conjunto de inversiones mediante indicadores claros y comparables.
### Objetivo:
Obtener un resumen financiero que incluya la mayor ganancia generada, la mayor perdida registrada y la ganancia total acumulada.
### Resultado Esperado:
Un conjunto compacto de valores que describa el comportamiento financiero del portafolio.
### Tecnologias Utilizadas:
Operaciones fold, tuplas y metodos de agregacion (max, min, sum).

## Ejercicio 5 - Organizacion del Capital por Nivel de Riesgo
### Contexto:
El banco necesita visualizar como esta distribuido su capital segun el nivel de riesgo asumido y poder gestionar dichas agrupaciones de manera flexible.
### Objetivo:
Organizar las inversiones segun su nivel de riesgo y permitir operaciones basicas de gestion sobre estas agrupaciones.
### Resultado Esperado:
El sistema debe permitir consultar inversiones por riesgo, agregar nuevas inversiones, eliminar inversiones existentes y verificar si un producto esta presente en un nivel de riesgo determinado.
### Tecnologias Utilizadas:
Map (diccionarios), operadores sobre colecciones (adicion, remocion) y gestion de jerarquia de tipos de Scala.