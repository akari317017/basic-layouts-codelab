package com.example.basiclayoutscodelab

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import com.example.basiclayoutscodelab.ui.theme.BasicLayoutsCodelabTheme


private const val BUTTON_LAYOUT_ID = "button"
private const val TEXT_LAYOUT_ID = "text"


@Composable
fun DecoupleConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) //縦長
        } else {
            decoupledConstraints(margin = 32.dp) //横長
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = {/*なにかする！*/ },
                modifier = Modifier.layoutId(BUTTON_LAYOUT_ID)
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId(TEXT_LAYOUT_ID))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor(BUTTON_LAYOUT_ID)
        val text = createRefFor(TEXT_LAYOUT_ID)
        constrain(button) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin = margin)
            centerHorizontallyTo(parent)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DecoupleConstraintLayoutPreview() {
    BasicLayoutsCodelabTheme {
        DecoupleConstraintLayout()
    }
}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(fraction = 0.5f)

        Text(
            text = "This is a very very very very very very very very very ver long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                //wrapにすると綺麗に0.5で配置できる！！！
                width = Dimension.preferredWrapContent
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LargeConstraintLayoutPreview() {
    BasicLayoutsCodelabTheme {
        LargeConstraintLayout()
    }
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        val (button1, button2, text) = createRefs()

        Button(onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text(text = "Button1")
        }


        Text(text = "Text", modifier = Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text(text = "Button2")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintLayoutContentPreview() {
    BasicLayoutsCodelabTheme {
        ConstraintLayoutContent()
    }
}