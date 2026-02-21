import  scala.annotation.tailrec
import javax.print.DocFlavor.INPUT_STREAM

// Caso de Uso: Modulo de Analisis de Inversiones – ECIBank
// ECIBank requiere el diseño de un módulo central para analizar el comportamiento de inversiones financieras de forma confiable, predecible y segura. 

// Case Class para representar una inversion:
case class InvestmentRecord(
  id: String, // identificador de la inversion
  product: String, // nombre del producto financiero
  riskLevel: String, // nivel de riesgo (LOW, MEDIUM, HIGH)
  investedAmount: BigDecimal,  // capital invertido
  monthlyReturn: Double,       // porcentaje
  month: Int    // mes de la inversion (1-12)
)

// Case Class para representar una tendencia de inversion:
case class TrendAnalysis(
  product: String,
  mesInicio: Int,
  mesFin: Int,
  gananciaInicial: BigDecimal,
  gananciaFinal: BigDecimal,
  cambio: BigDecimal,           // Diferencia entre periodos
  porcentajeCambio: Double,     // Cambio porcentual
  tendencia: String             // "INCREMENTO", "DISMINUCION", "ESTABLE", "CAMBIO_BRUSCO"
)

// DataSet para pruebas:
val dataset: List[InvestmentRecord] = List(
  InvestmentRecord("INV-01", "Fondo_A", "LOW",    BigDecimal(10000), 1.2, 1),
  InvestmentRecord("INV-02", "Fondo_A", "LOW",    BigDecimal(10000), 1.5, 2),
  InvestmentRecord("INV-03", "Fondo_A", "LOW",    BigDecimal(10000), 1.3, 3),
  InvestmentRecord("INV-04", "Fondo_B", "MEDIUM", BigDecimal(8000),  2.1, 1),
  InvestmentRecord("INV-05", "Fondo_B", "MEDIUM", BigDecimal(8000),  1.9, 2),
  InvestmentRecord("INV-06", "Fondo_B", "MEDIUM", BigDecimal(8000),  2.4, 3),
  InvestmentRecord("INV-07", "Accion_NVIDA", "HIGH",  BigDecimal(5000), -1.2, 1),
  InvestmentRecord("INV-08", "Accion_APPLE", "HIGH",  BigDecimal(5000),  3.4, 2),
  InvestmentRecord("INV-09", "Accion_GOOGLE", "HIGH",  BigDecimal(5000),  2.8, 3),
  InvestmentRecord("INV-10", "Bono_C",   "LOW",   BigDecimal(15000), 0.8, 1),
  InvestmentRecord("INV-11", "Bono_C",   "LOW",   BigDecimal(15000), 0.9, 2),
  InvestmentRecord("INV-12", "Bono_C",   "LOW",   BigDecimal(15000), 1.0, 3),
  InvestmentRecord("INV-13", "ETF",    "MEDIUM",BigDecimal(12000), 1.7, 1),
  InvestmentRecord("INV-14", "ETF",    "MEDIUM",BigDecimal(12000), 1.9, 2),
  InvestmentRecord("INV-15", "ETF",    "MEDIUM",BigDecimal(12000), 2.0, 3),
  InvestmentRecord("INV-16", "BTC", "HIGH",  BigDecimal(7000),  4.1, 1),
  InvestmentRecord("INV-17", "BTC", "HIGH",  BigDecimal(7000), -0.5, 2),
  InvestmentRecord("INV-18", "BTC", "HIGH",  BigDecimal(7000),  3.2, 3),
  InvestmentRecord("INV-19", "Fondo_D",  "LOW",   BigDecimal(9000),  1.1, 1),
  InvestmentRecord("INV-20", "Fondo_D",  "LOW",   BigDecimal(9000),  1.4, 2),
  InvestmentRecord("INV-21", "Fondo_X", "LOW",    BigDecimal(0),     1.5, 1),      
  InvestmentRecord("INV-22", "Accion_Y", "MEDIUM", BigDecimal(-5000), 2.0, 2),     
  InvestmentRecord("INV-23", "Crypto_Z", "HIGH",  BigDecimal(10000), 15.5, 3),     
  InvestmentRecord("INV-24", "Bono_W",   "LOW",   BigDecimal(8000),  -12.3, 1),    
  InvestmentRecord("INV-25", "ETF_V",    "EXTREME", BigDecimal(6000), 2.5, 2),     
  InvestmentRecord("INV-26", "Startup",  "VERY_HIGH", BigDecimal(3000), 5.0, 3), 
  InvestmentRecord("INV-27", "Proyecto", "UNKNOWN", BigDecimal(0), 20.0, 1),       
  InvestmentRecord("INV-28", "Fondo_K",  "SAFE",  BigDecimal(12000), -15.0, 2),    
  InvestmentRecord("INV-29", "Derivado", "HIGH",  BigDecimal(-1000), 8.0, 3),      
  InvestmentRecord("INV-30", "Hedge",    "MEDIUM", BigDecimal(5000),  25.7, 1)     
)

// Ejercicio 1 – Ganancia Total del Portafolio
// Contexto: ECIBank necesita conocer cuánto dinero ganó o perdió un portafolio completo de inversiones durante un perido específico.
// Objetivo: Calcular la ganancia monetaria total generada por todas las inversiones del portafolio, considerando tanto el capital invertido como su rendimiento.
// Resultado: Un unico valor monetario que represente la ganancia o perdida total del portafolio.
@tailrec
def calcularGananciaTotal(inversiones: List[InvestmentRecord], acumulador: BigDecimal = BigDecimal(0)): BigDecimal = {
    inversiones match {
        case Nil => acumulador //Caso base cuando la lista esta vacia
        case x::xs => // x es el primer elemento (una inversion) y xs es el resto de la lista de inversiones
            val ganancia = x.investedAmount * BigDecimal(x.monthlyReturn / 100) //Calcular la ganancia: Ganancia = investedAmount × (monthlyReturn / 100)
            calcularGananciaTotal(xs, acumulador + ganancia) //LLamada recursiva
    }
}

// Ejercicio 2 – Validacion de Inversiones
// Contexto:
// Antes de incluir una inversión en los reportes oficiales, el banco debe verificar que:
// El monto invertido sea valido
// El rendimiento este dentro de rangos aceptables
// El nivel de riesgo sea reconocido por la entidad
// Objetivo:
// Diseñar un proceso que determine si una inversión puede ser analizada o si debe ser rechazada, indicando la razon.
// Resultado Esperado:
// Una inversion aprobada para analisis
// O una explicación clara del motivo de rechazo

//Paso 1: Definir el tipo de resultado
type ValidationResult[A] = Either[String, InvestmentRecord] //String para mensaje de error, InvestmentRecord inversion validada

//Paso 2: Validar el monto invertido
def validarMonto(record: InvestmentRecord) : ValidationResult[InvestmentRecord] = {
   if (record.investedAmount > 0) {
    Right(record)
   } else {
     Left(s"Monto inválido para ${record.id}: debe ser mayor a 0") 
   } 
}

//Paso 3: Validar redimiento
def validarRendimiento(record: InvestmentRecord): ValidationResult[InvestmentRecord] = {
    if((-10 <= record.monthlyReturn) && (record.monthlyReturn <= 10)) {
        Right(record)
    } else {
        Left(s"Rendimiento fuera de rango para ${record.id}:: debe estar entre -10% y 10%")
    }
}

//Paso 3: Validar nivel de riego
def validarNivelRiesgo(record: InvestmentRecord): ValidationResult[InvestmentRecord] = {

    val riesgosValidos: Set[String] = Set("LOW", "MEDIUM", "HIGH")

    if(riesgosValidos.contains(record.riskLevel)) {
        Right(record)
    } else {
        Left(s"Nivel de riesgo no reconocido: ${record.riskLevel}")
    }
}

//Paso 4: Encadenar las validaciones con for-comprehension
def validarInversion(record: InvestmentRecord): ValidationResult[InvestmentRecord] = {
  for {
    r1 <- validarMonto(record)           // Si falla, se detiene aqui y retorna Left
    r2 <- validarRendimiento(r1)         // Solo se ejecuta si r1 fue Right
    r3 <- validarNivelRiesgo(r2)         // Solo se ejecuta si r2 fue Right
  } yield r3  // Si todas pasaron, retorna Right(record)
}

//Paso 5: Mostrar resutado de analisis con pattern matching
def mostrarResultadoAnalisis(record: InvestmentRecord): Unit = {
    validarInversion(record) match {
        case Right(inv) => println(s"${inv.id} aprobada para analisis")
        case Left(error) => println(s"Rechazada: $error")
    } 
}

//Paso 6: Validar todo el dataset
def validarPortafolio(inversiones: List[InvestmentRecord]): Unit = {
  inversiones.map(inv => mostrarResultadoAnalisis(inv))
}

// Ejercicio 3 – Analisis de Tendencias de Ganancia
// Contexto:
// Los analistas financieros quieren identificar como evoluciona la ganancia real de un producto a lo largo del tiempo para detectar comportamientos positivos o señales de alerta.
// Objetivo
// Analizar la evolucion temporal de una inversion comparando periodos consecutivos y enfocandose en rangos especificos de tiempo.
// Resultado Esperado:
// Una estructura que permita identificar
// Incrementos
// Disminuciones
// Cambios bruscos en la ganancia generada

// Paso 1: Funcion para calcular la ganacia de una inversion
def calcularGanancia(record: InvestmentRecord) :  BigDecimal = {
    record.investedAmount * BigDecimal(record.monthlyReturn / 100)
}

//Paso 2: Funcion principal para analizar tendencias
//Filtra inversiones del producto y ordena por mes
//Usa sliding(2) para obtener pares consecutivos
//Para cada par, calcula ganancias, cambio, porcentaje y clasifica
//Retorna lista de TrendAnalysis
def analizarTendenciasProducto(producto: String, inversiones: List[InvestmentRecord]) : List[TrendAnalysis] = {

    // Filtrar por producto y ordernar por mes
    val inversionesProducto = inversiones
        .filter(_.product == producto)
        .sortBy(_.month)

    // Usar sliing para crear pares consecutivos de inversiones y analizarlas
    inversionesProducto
        .sliding(2)
        .collect {
            case List(inv1, inv2) =>
                val gananciaInicial = calcularGanancia(inv1)
                val gananciaFinal = calcularGanancia(inv2)
                val cambio = gananciaFinal - gananciaInicial
                
                // Calcular cambio porcentual
                val porcentajeCambio = if (gananciaInicial != 0) {
                    (((gananciaFinal - gananciaInicial) / gananciaInicial) * 100).toDouble
                } else {
                0.0
                }

                // Clasificar tendencia
                val tendencia = if (Math.abs(porcentajeCambio) > 50) {
                "CAMBIO_BRUSCO"
                } else if (porcentajeCambio > 10) {
                "INCREMENTO"
                } else if (porcentajeCambio < -10) {
                "DISMINUCIÓN"
                } else {
                "ESTABLE"
                }
                
                // Crear el analisis
                TrendAnalysis(
                product = producto,
                mesInicio = inv1.month,
                mesFin = inv2.month,
                gananciaInicial = gananciaInicial,
                gananciaFinal = gananciaFinal,
                cambio = cambio,
                porcentajeCambio = porcentajeCambio,
                tendencia = tendencia
                )
        }
        .toList
}

def mostrarTendencias(tendencias: List[TrendAnalysis]): Unit = {
    tendencias.map(t => 
        println(s"${t.product} [Mes ${t.mesInicio}->${t.mesFin}]: ${t.tendencia}")
        println(s"  Ganancia: $$${t.gananciaInicial} -> $$${t.gananciaFinal} (${t.porcentajeCambio.formatted("%.2f")}%)\n")
    )
}
// Ejercicio 4 – Indicadores Financieros 
// Contexto:
// Para reportes ejecutivos, ECIBank necesita resumir el desempeño financiero de un conjunto de inversiones mediante indicadores claros y comparables.
// Objetivo:
// Obtener un resumen financiero que incluya
// La mayor ganancia generada
// La mayor perdida registrada
// La ganancia total acumulada
// Resultado Esperado
// Un conjunto compacto de valores que describa el comportamiento financiero del portafolio.
def calcularIndicadores(inversiones: List[InvestmentRecord]): (BigDecimal, BigDecimal, BigDecimal) = {
  // Calcular todas las ganancias
  val ganancias = inversiones.map(calcularGanancia)
  
  // Usar fold para el total y max/min para los extremos
  val total = ganancias.foldLeft(BigDecimal(0))((acc, ganancia) => acc + ganancia)
  
  //Devolver la tupla con los tres indicadores
  (ganancias.max, ganancias.min, total)
}
//Funcion para visualiasacion
def mostrarIndicadores(indicadores: (BigDecimal, BigDecimal, BigDecimal)): Unit = {
  val (maxGanancia, maxPerdida, total) = indicadores
  
  println(s"Indicadores Financieros")
  println(s"Mayor ganancia:  $$${maxGanancia}")
  println(s"Mayor perdida:   $$${maxPerdida}")
  println(s"Ganancia total:  $$${total}")
}

// Ejercicio 5 – Organizacion del Capital por Nivel de Riesgo
// Contexto:
// El banco necesita visualizar como esta distribuido su capital segun el nivel de riesgo asumido
// y poder gestionar dichas agrupaciones de manera flexible.
// Objetivo:
// Organizar las inversiones segun su nivel de riesgo
// Resultado Esperado
// El sistema debe permitir:
// Consultar inversiones por riesgo
// Agregar nuevas inversiones
// Eliminar inversiones existentes
// Verificar si un producto esta presente en un nivel de riesgo determinado

//Paso 1: Organizar las inversiones por su nivel de riesgo
def organizarPorRiesgo(inversiones: List[InvestmentRecord]): Map[String, List[InvestmentRecord]] = {
  inversiones.groupBy(_.riskLevel) //Agrupar las inverciones por su nivel de riesgo
}
//Paso 2: Consultar inversiones por riego
def consultarPorRiesgo(portafolio: Map[String, List[InvestmentRecord]], riesgo: String ): List[InvestmentRecord] = {
  portafolio.getOrElse(riesgo, List.empty)
}
//Paso 3: Agregar una nueva inversion
def agregarInversion(portafolio: Map[String, List[InvestmentRecord]], nuevaInversion: InvestmentRecord
): Map[String, List[InvestmentRecord]] = {
  
  val riesgo = nuevaInversion.riskLevel
  val inversionesActuales = portafolio.getOrElse(riesgo, List.empty)
  val inversionesActualizadas = nuevaInversion :: inversionesActuales
  
  portafolio + ((riesgo, inversionesActualizadas))
}
//Paso 4: Eliminar una inversion
def eliminarInversionDeRiesgo( portafolio: Map[String, List[InvestmentRecord]], 
  riesgo: String,
  inversionId: String
): Map[String, List[InvestmentRecord]] = {
  
  val inversionesActuales = portafolio.getOrElse(riesgo, List.empty)
  val inversionesActualizadas = inversionesActuales.filterNot(_.id == inversionId)
  
  portafolio + ((riesgo, inversionesActualizadas))
}
//Paso 5: Verificar si un producto existe
def existeProductoEnRiesgo(portafolio: Map[String, List[InvestmentRecord]], 
  producto: String, 
  riesgo: String
): Boolean = {
  portafolio
    .getOrElse(riesgo, List.empty)
    .exists(_.product == producto)
}

@main def ejecutar(): Unit = {
    println("Taller 3 - Programación Funcional\n")

    println("Ejercicio 1 - Estructura y recursion:")
    val gananciaTotalPortafolio = calcularGananciaTotal(dataset)
    println(s"Ganancia total del portafolio: $$${gananciaTotalPortafolio}\n")

    println("Ejercicio 2 - Seguridad y contexto:")
    println(validarPortafolio(dataset))

    println("Ejercicio 3 - Transformacion")
    // Analizar tendencias de bitcoin
    val tendeciasBTC = analizarTendenciasProducto("BTC", dataset)
    mostrarTendencias(tendeciasBTC)
    // Analizar tendencias un fondo
    val tendenciasFondoA = analizarTendenciasProducto("Fondo_A", dataset)
    mostrarTendencias(tendenciasFondoA)

    println("\nEjercicio 4 - Agregacion:")
    val indicadores = calcularIndicadores(dataset)
    mostrarIndicadores(indicadores)

    println("\nEjercicio 5 - Gestión y jerarquia:")

    // Crear portafolio inicial
    var portafolioRiesgo = organizarPorRiesgo(dataset)
    //Consultar inversiones de riesgo HIGH
    val inversionesAltas = consultarPorRiesgo(portafolioRiesgo, "HIGH")
    println(s"Inversiones HIGH: ${inversionesAltas.size}")

    //Agregar nueva inversión
    val nuevaInv = InvestmentRecord("INV-31", "Bono_Z", "LOW", BigDecimal(20000), 0.5, 4)
    portafolioRiesgo = agregarInversion(portafolioRiesgo, nuevaInv)

    //Eliminar una inversión
    portafolioRiesgo = eliminarInversionDeRiesgo(portafolioRiesgo, "LOW","INV-21")

    // 4. Verificar si existe un producto
    val existeBTC = existeProductoEnRiesgo(portafolioRiesgo, "BTC", "HIGH")
    println(s"Existe BTC en HIGH?: $existeBTC")
}